package org.seppiko.wagashi.core.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.seppiko.commons.logging.Logging;
import org.seppiko.commons.logging.LoggingFactory;
import org.seppiko.wagashi.core.models.ResponseMessageEntity;
import org.seppiko.wagashi.core.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

/**
 * @author Leonard Woo
 */
@ControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

  private final Logging log = LoggingFactory.getLogging(this.getClass());

  @ResponseBody
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable cause) {
    HttpStatus status = getStatus(request);
    ArrayList<ResponseMessageEntity<?>> errorList = new ArrayList<>();
    errorList.add(new ResponseMessageEntity<>(status.value(), "SERVER ERROR!!! " + cause.getMessage()));
    log.error("GLOBAL ERROR: " + cause.getMessage());
    return ResponseUtil.sendJson(status, new ResponseMessageEntity<>(errorList));
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    HttpStatus status = HttpStatus.resolve(code);
    return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
  }

}

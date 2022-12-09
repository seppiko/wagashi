package org.seppiko.wagashi.core.controllers;

import org.seppiko.commons.utils.StringUtil;
import org.seppiko.wagashi.commons.utils.JsonUtil;
import org.seppiko.wagashi.commons.utils.JwtUtil;
import org.seppiko.wagashi.core.models.ResponseMessageEntity;
import org.seppiko.wagashi.core.models.ResponseTokenEntity;
import org.seppiko.wagashi.core.services.UserPrincipal;
import org.seppiko.wagashi.core.services.UserService;
import org.seppiko.wagashi.core.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leonard Woo
 */
@RestController
public class AccountController {

  @Autowired
  private UserService service;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<byte[]> loginContentHandleExecution(@RequestBody(required = false) String requestBody) {
    if (requestBody == null || StringUtil.hasLength(requestBody)) {
      return ResponseUtil.sendJson(400, new ResponseMessageEntity<>(400, "failure"));
    }

    var login = JsonUtil.fromJsonObject(requestBody);
    if (login.isEmpty()) {
      return ResponseUtil.sendJson(200, new ResponseMessageEntity<>(400, "failure"));
    }

    UserDetails userDetails = service.loadUserByUsername(login.get("username").asText());
    if (!login.get("password").asText().equals(userDetails.getPassword())) {
      return ResponseUtil.sendJson(200, new ResponseMessageEntity<>(401, "failure"));
    }

    String jwt = JwtUtil.jwtGenerator(userDetails.getUsername());
    ResponseTokenEntity respToken = new ResponseTokenEntity(jwt, JwtUtil.getExpires());

    return ResponseUtil.sendJson(200, new ResponseMessageEntity<>(200, "ok", respToken));
  }

  @RequestMapping(value = "/logout")
  public ResponseEntity<byte[]> logoutContentHandleExecution() {
    return ResponseUtil.sendJson(200, new ResponseMessageEntity<>(200, "ok"));
  }

}

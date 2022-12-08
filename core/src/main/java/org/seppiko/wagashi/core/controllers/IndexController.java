/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.seppiko.wagashi.core.controllers;

import java.util.Map;
import org.seppiko.commons.logging.Logging;
import org.seppiko.commons.logging.LoggingFactory;
import org.seppiko.wagashi.commons.utils.JsonUtil;
import org.seppiko.wagashi.core.models.ResponseMessageEntity;
import org.seppiko.wagashi.core.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author Leonard Woo
 */
@RestController
public class IndexController {

  private final Logging log = LoggingFactory.getLogging(this.getClass());

  @RequestMapping(value = "/")
  public ModelAndView indexContentHandleExecution(
      @RequestParam(required = false) Map<String, String> params,
      @RequestBody(required = false) String requestBody) {

    if (null != params && !params.isEmpty()) {
      log.info(JsonUtil.toJson(params));
    }
    if (requestBody != null) {
      log.info(requestBody);
    }

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setView(new MappingJackson2JsonView());
    modelAndView.setStatus(HttpStatus.OK);
    modelAndView.addObject("code", 400);
    modelAndView.addObject("message", "Bad request");
    return modelAndView;
  }

  @RequestMapping(value = "/settings", method = RequestMethod.GET)
  public ResponseEntity<byte[]> indexContentHandleExecution() {
    return ResponseUtil.sendJson(200, new ResponseMessageEntity<>(200, "ok"));
  }

}

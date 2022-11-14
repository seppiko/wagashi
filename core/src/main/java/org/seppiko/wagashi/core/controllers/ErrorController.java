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
import javax.servlet.http.HttpServletRequest;
import org.seppiko.wagashi.utils.LoggingManager;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author Leonard Woo
 */
@RestController
public class ErrorController implements ErrorViewResolver {

  private final LoggingManager logger = LoggingManager.getLogger(this.getClass());

  @Override
  public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status,
      Map<String, Object> model) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setView(new MappingJackson2JsonView());
    if (status.is4xxClientError()) {
      modelAndView.setStatus(status);
    } else {
      modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    modelAndView.addObject("code", status.value());
    modelAndView.addObject("message", "SERVER ERROR!!!");
    logger.warn("GLOBAL ERROR: " + modelAndView.getModelMap().toString());
    return modelAndView;
  }
}

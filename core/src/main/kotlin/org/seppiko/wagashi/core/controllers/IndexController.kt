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

package org.seppiko.wagashi.core.controllers

import org.seppiko.commons.logging.Logging
import org.seppiko.commons.logging.LoggingFactory
import org.seppiko.wagashi.commons.utils.JsonUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

/**
 *
 * @author Leonard Woo
 */
@RestController
class IndexController {

  private val logger: Logging = LoggingFactory.getLogging(this::class.qualifiedName)!!

  @RequestMapping(value = ["/"])
  fun indexContentHandleExecution(
    @RequestParam(required = false) params: Map<String?, String?>?,
    @RequestBody(required = false) requestBody: String?
  ): ModelAndView? {

    logger.info(JsonUtil.toJson(params))

    if (requestBody != null) {
      logger.info(requestBody)
    }

    val modelAndView = ModelAndView()
    modelAndView.view = MappingJackson2JsonView()
    modelAndView.status = HttpStatus.OK
    modelAndView.addObject("code", 400)
    modelAndView.addObject("message", "Bad request")
    return modelAndView
  }
}
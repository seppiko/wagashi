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

import org.seppiko.wagashi.core.models.ResponseMessage;
import org.seppiko.wagashi.core.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leonard Woo
 */
@RestController
public class AccountController {

 @RequestMapping(value = "/login", method = RequestMethod.POST)
 public ResponseEntity<byte[]> ContentHandleExecution(
     @RequestBody(required = false) String requestBody) {

  return ResponseUtil.sendJson(200, new ResponseMessage<>(200, "ok"));
 }

}

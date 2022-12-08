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

import org.seppiko.wagashi.core.models.ResponseMessage
import org.seppiko.wagashi.core.services.UserService
import org.seppiko.wagashi.core.utils.ResponseUtil.sendJson
import org.seppiko.wagashi.commons.utils.JsonUtil
import org.seppiko.wagashi.commons.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors


/**
 *
 * @author Leonard Woo
 */
@RestController
class AccountController {

  @Autowired
  private lateinit var service: UserService


  @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
  fun ContentHandleExecution(
    @RequestBody(required = false) requestBody: String?
  ): ResponseEntity<ByteArray?>? {

    if (requestBody.isNullOrBlank()) {
      return sendJson(400, ResponseMessage<Any>(400, "failure"))
    }

    val login = JsonUtil.fromJsonObject(requestBody)
    val user = service.loadUserByUsername(login!!.get("username").asText())
    if (!login.get("password").asText().equals(user.password)) {
      return sendJson(200, ResponseMessage<Any>(401, "failure"))
    }

    val jwt = JwtUtil.generator(user.username)

//    val roles: List<String> = user.authorities.stream()
//      .map { item -> item.authority }
//      .collect(Collectors.toList())

    val map = mapOf(
      "access_token" to jwt,
      "token_type" to "Bearer",
      "expires_in" to 3600,
    )

    return sendJson(200, ResponseMessage<Any>(200, "ok", map))
  }

}
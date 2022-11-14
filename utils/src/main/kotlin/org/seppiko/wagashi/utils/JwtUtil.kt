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

package org.seppiko.wagashi.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException

/**
 *
 * @author Leonard Woo
 */
object JwtUtil {
 private val algorithm = Algorithm.HMAC256("")

 fun generator(): String? {
  return try {
   JWT.create()
    .withIssuer("")
    .sign(algorithm)
  } catch (exception: JWTCreationException) {
   // Invalid Signing configuration / Couldn't convert Claims.
   throw IllegalCallerException("")
  }
 }

 fun verify(token: String?): Boolean {
  return try {
   val verifier = JWT.require(algorithm).build()
   val decodedJWT = verifier.verify(token)
   !decodedJWT.payload.equals("")
  } catch (exception: JWTVerificationException) {
   // Invalid signature/claims
   throw IllegalCallerException("")
  }
 }
}
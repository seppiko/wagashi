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
package org.seppiko.wagashi.core.models

/**
 * @author Leonard Woo
 */
class ResponseMessage<T> {

  var code: Int?
  var message: String?
  var payload: T?
  var errors: ArrayList<ResponseMessage<*>?>?

  constructor(code: Int?, message: String?) : this(code, message, null, null)

  constructor(code: Int?, message: String?, payload: T) : this(code, message, payload, null)

  constructor(errors: ArrayList<ResponseMessage<*>?>?) : this(null, null, null, errors)

  constructor(code: Int?, message: String?, payload: T?, errors: ArrayList<ResponseMessage<*>?>?) {
    this.code = code
    this.message = message
    this.payload = payload
    this.errors = errors
  }

}
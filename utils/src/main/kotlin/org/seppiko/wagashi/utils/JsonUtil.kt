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

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paranamer.ParanamerModule
import org.seppiko.commons.utils.ObjectUtil
import org.seppiko.commons.utils.StringUtil
import java.io.IOException
import java.io.Reader
import java.io.StringReader

/**
 *
 * @author Leonard Woo
 */
object JsonUtil {
  private val logger: LoggingManager = LoggingManager.getLogger(JsonUtil::class.toString())!!

  private val mapper = ObjectMapper()

  var NULL_JSON_NODE: JsonNode?

  init {
    mapper.registerModule(ParanamerModule())

    mapper.setSerializationInclusion(Include.NON_NULL)
    mapper.setSerializationInclusion(Include.NON_EMPTY)

    mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
    mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false)

    mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)
    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)

    NULL_JSON_NODE = mapper.createObjectNode()
  }

  fun <T> toJson(t: T): String {
    if (ObjectUtil.isNull(t)) {
      return ""
    }
    try {
      return mapper.writeValueAsString(t)
    } catch (e: JsonProcessingException) {
      logger.warn("Json string generator exception.", e)
    }
    return "{}"
  }

  fun <T> fromJson(json: String?, type: Class<T>): T? {
    if (ObjectUtil.isNull(type) || !StringUtil.hasText(json)) {
      return null
    }
    try {
      return mapper.readValue(json, type)
    } catch (e: JsonProcessingException) {
      logger.warn("Json string parse exception. " + e.message)
    }
    return null
  }

  fun fromJsonObject(json: String?): JsonNode? {
    return if (!StringUtil.hasText(json)) {
      NULL_JSON_NODE
    } else fromJsonObject(StringReader(json))
  }

  fun fromJsonObject(json: Reader): JsonNode? {
    ObjectUtil.isNull(json)
    try {
      return mapper.reader().readTree(json)
    } catch (e: IOException) {
      logger.warn("Json parse exception.", e)
    }
    return NULL_JSON_NODE
  }

  fun <T> toT(node: JsonNode?, type: Class<T>?): T? {
    try {
      return mapper.treeToValue(node, type)
    } catch (e: JsonProcessingException) {
      logger.warn("Json object parse exception.", e)
    }
    return null
  }

  fun <T> convert2T(obj: Any?, type: Class<T>?): T? {
    try {
      return mapper.convertValue(obj, type)
    } catch (ex: IllegalArgumentException) {
      logger.warn("Object convert exception.", ex)
    }
    return null
  }
}
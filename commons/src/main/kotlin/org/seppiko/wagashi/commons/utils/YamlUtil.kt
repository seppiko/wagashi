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

package org.seppiko.wagashi.commons.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.seppiko.commons.logging.Logging
import org.seppiko.commons.logging.LoggingFactory
import org.seppiko.commons.utils.ObjectUtil
import org.seppiko.commons.utils.StringUtil
import java.io.IOException
import java.io.Reader
import java.io.StringReader

/**
 *
 * @author Leonard Woo
 */
object YamlUtil {
  private val logger: Logging = LoggingFactory.getLogging(JsonUtil::class.toString())!!

  private val mapper = ObjectMapper(YAMLFactory())

  init {
    mapper.findAndRegisterModules()

    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

    mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
    mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false)

    mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)
    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
  }

  fun <T> toYaml(t: T): String {
    if (ObjectUtil.isNull(t)) {
      return ""
    }
    try {
      return mapper.writeValueAsString(t)
    } catch (e: JsonProcessingException) {
      logger.warn("Yaml string generator exception.", e)
    }
    return ""
  }

  fun <T> fromYaml(yaml: String?, type: Class<T>): T? {
    if (ObjectUtil.isNull(type) || !StringUtil.hasText(yaml)) {
      return null
    }
    try {
      return mapper.readValue(yaml, type)
    } catch (e: JsonProcessingException) {
      logger.warn("Yaml string parse exception. " + e.message)
    }
    return null
  }

  fun fromYamlObject(yaml: String): JsonNode? {
    return if (!StringUtil.hasText(yaml)) {
      JsonUtil.NULL_JSON_NODE
    } else fromYamlObject(StringReader(yaml))
  }

  fun fromYamlObject(yaml: Reader): JsonNode? {
    ObjectUtil.isNull(yaml)
    try {
      return mapper.reader().readTree(yaml)
    } catch (e: IOException) {
      logger.warn("Yaml parse exception.", e)
    }
    return JsonUtil.NULL_JSON_NODE
  }

}
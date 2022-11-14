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

package org.seppiko.wagashi.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.seppiko.commons.utils.ObjectUtil;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.seppiko.commons.utils.StringUtil;

/**
 * Jackson json util
 *
 * @author Leonard Woo
 */
public class JsonUtil {

  private static final LoggingManager logger = LoggingManager.getLogger(JsonUtil.class);

  private static final ObjectMapper mapper = new ObjectMapper();

  public static final JsonNode NULL_JSON_NODE;

  static {
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.setSerializationInclusion(Include.NON_EMPTY);

    mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);

    mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

    NULL_JSON_NODE = mapper.createObjectNode();
  }

  public static <T> String toJson(T t) {
    if (ObjectUtil.isNull(t)) {
      return "";
    }
    try {
      return mapper.writeValueAsString(t);
    } catch (JsonProcessingException e) {
      logger.warn("Json string generator exception.", e);
    }
    return "{}";
  }

  public static <T> T fromJson(String json, Class<T> type) {
    if (ObjectUtil.isNull(type) || !StringUtil.hasText(json)) {
      return null;
    }
    try {
      return mapper.readValue(json, type);
    } catch (JsonProcessingException e) {
      logger.warn("Json string parse exception. " + e.getMessage());
    }
    return null;
  }

  public static JsonNode fromJsonObject(String json) {
    if (!StringUtil.hasText(json)) {
      return NULL_JSON_NODE;
    }
    return fromJsonObject(new StringReader(json));
  }

  public static JsonNode fromJsonObject(Reader json) {
    if (ObjectUtil.isNull(json)) {
      throw new IllegalArgumentException();
    }
    try {
      return mapper.reader().readTree(json);
    } catch (IOException e) {
      logger.warn("Json parse exception.", e);
    }
    return NULL_JSON_NODE;
  }

  public static <T> T toT(JsonNode node, Class<T> type) {
    try {
      return mapper.treeToValue(node, type);
    } catch (JsonProcessingException e) {
      logger.warn("Json object parse exception.", e);
    }
    return null;
  }

  public static <T> T convert2T(Object obj, Class<T> type) {
    try {
      return mapper.convertValue(obj, type);
    } catch (IllegalArgumentException ex) {
      logger.warn("Object convert exception.", ex);
    }
    return null;
  }

}

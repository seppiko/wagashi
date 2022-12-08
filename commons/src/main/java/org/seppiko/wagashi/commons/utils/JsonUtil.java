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

package org.seppiko.wagashi.commons.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paranamer.ParanamerModule;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.seppiko.commons.logging.Logging;
import org.seppiko.commons.logging.LoggingFactory;
import org.seppiko.commons.utils.ObjectUtil;

/**
 * Jackson util
 *
 * @author Leonard Woo
 */
public class JsonUtil {

  private static final Logging log = LoggingFactory.getLogging(JsonUtil.class);

  private static final ObjectMapper mapper = new ObjectMapper();

  public static final JsonNode NULL_JSONNODE;

  static {
    mapper.registerModule(new ParanamerModule());

    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.setSerializationInclusion(Include.NON_EMPTY);

    mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
    mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

    NULL_JSONNODE = mapper.nullNode();
  }

  public static <T> String toJson(T t) {
    if (ObjectUtil.isNull(t)) {
      return "";
    }
    try {
      return mapper.writeValueAsString(t);
    } catch (JsonProcessingException e) {
      log.warn("Json string generator exception.", e);
    }
    return "{}";
  }

  public static <T> T fromJson(String json, Class<T> type) {
    try {
      return mapper.readValue(json, type);
    } catch (JsonProcessingException e) {
      log.warn("Json string parse exception.", e);
    }
    return null;
  }

  public static JsonNode fromJsonObject(String json) {
    return fromJsonObject(new StringReader(json));
  }

  public static JsonNode fromJsonObject(Reader json) {
    try {
      return mapper.reader().readTree(json);
    } catch (IOException e) {
      log.warn("Json parse exception.", e);
    }
    return NULL_JSONNODE;
  }

  public static <T> T toT(JsonNode node, Class<T> type) {
    try {
      return mapper.treeToValue(node, type);
    } catch (JsonProcessingException e) {
      log.warn("Json object parse exception.", e);
    }
    return null;
  }

  public static <T> T convert2T(Object obj, Class<T> type) {
    try {
      return mapper.convertValue(obj, type);
    } catch (IllegalArgumentException ex) {
      log.warn("Object convert exception.", ex);
    }
    return null;
  }

}

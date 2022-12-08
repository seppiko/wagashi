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
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Objects;
import org.seppiko.commons.logging.Logging;
import org.seppiko.commons.logging.LoggingFactory;
import org.seppiko.commons.utils.ObjectUtil;
import org.seppiko.commons.utils.StringUtil;

/**
 * @author Leonard Woo
 */
public class YamlUtil {

  private static final Logging log = LoggingFactory.getLogging(YamlUtil.class);

  private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

  static {
    mapper.findAndRegisterModules();

    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.setSerializationInclusion(Include.NON_EMPTY);

    mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);

    mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
  }

  public static <T> String toYaml(T t) {
    if (ObjectUtil.isNull(t)) {
      return "";
    }
    try {
      return mapper.writeValueAsString(t);
    } catch (JsonProcessingException e) {
      log.warn("Yaml string generator exception.", e);
    }
    return "";
  }

  public static <T> T fromYaml(String yaml, Class<T> type) {
    if (ObjectUtil.isNull(type) || !StringUtil.hasText(yaml)) {
      return null;
    }
    try {
      return mapper.readValue(yaml, type);
    } catch (JsonProcessingException e) {
      log.warn("Yaml string parse exception. " + e.getMessage());
    }
    return null;
  }

  public static JsonNode fromYamlObject(String yaml) {
    if (!StringUtil.hasText(yaml)) {
      return JsonUtil.NULL_JSONNODE;
    }
    return fromYamlObject(new StringReader(yaml));
  }

  public static JsonNode fromYamlObject(Reader yaml) throws NullPointerException {
    Objects.requireNonNull(yaml);
    try {
      return mapper.reader().readTree(yaml);
    } catch (IOException e) {
      log.warn("Yaml parse exception.", e);
    }
    return JsonUtil.NULL_JSONNODE;
  }
}

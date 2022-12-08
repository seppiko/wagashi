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

package org.seppiko.wagashi.commons.configurations;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.seppiko.commons.logging.Logging;
import org.seppiko.commons.logging.LoggingFactory;
import org.seppiko.commons.utils.Environment;
import org.seppiko.commons.utils.StreamUtil;
import org.seppiko.wagashi.commons.models.JdbcEntity;
import org.seppiko.wagashi.commons.models.JwtEntity;
import org.seppiko.wagashi.commons.utils.JsonUtil;
import org.seppiko.wagashi.commons.utils.YamlUtil;

/**
 * @author Leonard Woo
 */
public class WagashiConfigure {

  private static final WagashiConfigure INSTANCE = new WagashiConfigure();
  public static WagashiConfigure getInstance() {
    return INSTANCE;
  }

  private final Logging log = LoggingFactory.getLogging(this.getClass());

  private WagashiConfigure() {
    init();
  }

  private JdbcEntity jdbcConfig;

  public JdbcEntity getJdbcConfig() {
    return jdbcConfig;
  }

  private JwtEntity jwtConfig;

  public JwtEntity getJwtConfig() {
    return jwtConfig;
  }

  private void init() {
    String configPathname = System.getProperty("wagashi." + Environment.CONFIG_FILE_PARAMETER_SUFFIX,
        Environment.CONFIG_FILENAME_YAML);
    try {
      InputStream cis = StreamUtil.getStream(configPathname);
      if (null == cis) {
        cis = StreamUtil.getStream(Environment.CONFIG_FILENAME_YML);
      }
      if (null == cis) {
        cis = StreamUtil.getStream(StreamUtil.findFile(this.getClass(), Environment.CONFIG_FILENAME_YAML));
      }
      if (null == cis) {
        cis = StreamUtil.getStream(StreamUtil.findFile(this.getClass(), Environment.CONFIG_FILENAME_YML));
      }
      if (null == cis) {
        throw new FileNotFoundException("config.yaml or config.yml not found");
      }

      BufferedReader reader = StreamUtil.loadReader(cis);

      JsonNode root = YamlUtil.fromYamlObject(reader);
      log.info("Config: " + root.toString());
      loadConfig(root);

    } catch (Throwable cause) {
      log.atError().withCause(cause).log();
    }
  }

  private void loadConfig(JsonNode root) {
    this.jdbcConfig = JsonUtil.toT(root.get("jdbc"), JdbcEntity.class);

    this.jwtConfig = JsonUtil.toT(root.get("jwt"), JwtEntity.class);
  }

}

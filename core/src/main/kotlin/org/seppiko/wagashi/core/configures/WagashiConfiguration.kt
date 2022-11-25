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

package org.seppiko.wagashi.core.configures

import com.fasterxml.jackson.databind.JsonNode
import org.seppiko.commons.utils.Environment
import org.seppiko.commons.utils.ObjectUtil
import org.seppiko.commons.utils.StreamUtil
import org.seppiko.wagashi.utils.JsonUtil
import org.seppiko.wagashi.utils.LoggingManager
import org.seppiko.wagashi.utils.YamlUtil
import java.io.FileNotFoundException


/**
 *
 * @author Leonard Woo
 */
object WagashiConfiguration {

  private val logger: LoggingManager = LoggingManager.Companion.getLogger(this::class.qualifiedName)!!

  var jdbc: JdbcEntity? = null

  var jwt: JwtEntity? = null

  init {
    try {
      val filepath = System.getProperty("wagashi." + Environment.CONFIG_FILE_PARAMETER_SUFFIX,
        Environment.CONFIG_FILENAME_YAML)
      var `is` = StreamUtil.getStream(filepath)
      if (ObjectUtil.isNull(`is`)) {
        `is` = StreamUtil.getStream(Environment.CONFIG_FILENAME_YML)
      }
      if (ObjectUtil.isNull(`is`)) {
        `is` = StreamUtil.getStream(StreamUtil.findFile(this::class.java, Environment.CONFIG_FILENAME_YAML))
      }
      if (ObjectUtil.isNull(`is`)) {
        `is` = StreamUtil.getStream(StreamUtil.findFile(this::class.java, Environment.CONFIG_FILENAME_YML))
      }

      if (ObjectUtil.isNull(`is`)) {
        throw FileNotFoundException("config.yaml or config.yml not found")
      }
      val reader = StreamUtil.loadReader(`is`)

      val root = YamlUtil.fromYamlObject(reader)
      logger.info("Config: " + root.toString())
      loadConfig(root!!)

    } catch (t: Throwable) {
      logger.error("", t);
    }
  }

  private fun loadConfig(root: JsonNode) {
    this.jdbc = JsonUtil.toT(root.get("jdbc"), JdbcEntity::class.java)
    this.jwt = JsonUtil.toT(root.get("jwt"), JwtEntity::class.java)
  }

}
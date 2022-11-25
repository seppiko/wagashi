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

package org.seppiko.wagashi.test

import org.junit.jupiter.api.Test
import org.seppiko.wagashi.core.configures.WagashiConfiguration
import org.seppiko.wagashi.core.utils.JwtUtil
import org.seppiko.wagashi.utils.JsonUtil
import org.seppiko.wagashi.utils.LoggingManager
import org.springframework.boot.test.context.SpringBootTest

/**
 *
 * @author Leonard Woo
 */
@SpringBootTest
class WagashiTest {

  private val logger: LoggingManager = LoggingManager.Companion.getLogger(this::class.qualifiedName)!!

  @Test
  fun configTest() {
   logger.info(JsonUtil.toJson(WagashiConfiguration.jdbc))
  }

  @Test
  fun jwtTest() {
    JwtUtil.generator("123")?.let { logger.info(it) }
  }
}
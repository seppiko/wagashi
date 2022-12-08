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

import io.undertow.server.DefaultByteBufferPool
import io.undertow.servlet.api.DeploymentInfo
import io.undertow.websockets.jsr.WebSocketDeploymentInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component

/**
 * Undertow Configuration
 *
 * @author Leonard Woo
 */
@Component
class UndertowConfiguration: WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

  @Value("\${spring.undertow.buffer-size}")
  private val bufferSize = 0


  override fun customize(factory: UndertowServletWebServerFactory?) {
    factory!!.addDeploymentInfoCustomizers(UndertowDeploymentInfoCustomizer { deploymentInfo: DeploymentInfo ->
      val webSocketDeploymentInfo = WebSocketDeploymentInfo()
      webSocketDeploymentInfo.buffers = DefaultByteBufferPool(false, bufferSize)
      deploymentInfo.addServletContextAttribute(
        "io.undertow.websockets.jsr.WebSocketDeploymentInfo",
        webSocketDeploymentInfo
      )
    })
  }
}
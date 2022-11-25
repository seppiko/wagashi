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

import org.seppiko.commons.logging.ILogging
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author Leonard Woo
 */
class LoggingManager(private var logger: Logger?) : ILogging {

  companion object {
    fun getLogger(clazz: Class<*>): LoggingManager? {
      return getLogger(clazz.name)
    }

    fun getLogger(name: String?): LoggingManager? {
      return LoggingManager(LoggerFactory.getLogger(name))
    }
  }

  override fun isTraceEnable(): Boolean {
    return logger!!.isTraceEnabled
  }

  override fun trace(message: CharSequence) {
    logger!!.trace(message.toString())
  }

  override fun trace(message: CharSequence, cause: Throwable?) {
    logger!!.trace(message.toString(), cause)
  }

  override fun isDebugEnable(): Boolean {
    return logger!!.isDebugEnabled
  }

  override fun debug(message: CharSequence) {
    logger!!.debug(message.toString())
  }

  override fun debug(message: CharSequence, cause: Throwable?) {
    logger!!.debug(message.toString(), cause)
  }

  override fun isInfoEnable(): Boolean {
    return logger!!.isInfoEnabled
  }

  override fun info(message: CharSequence) {
    logger!!.info(message.toString())
  }

  override fun info(message: CharSequence, cause: Throwable?) {
    logger!!.info(message.toString(), cause)
  }

  override fun isWarnEnable(): Boolean {
    return logger!!.isWarnEnabled
  }

  override fun warn(message: CharSequence) {
    logger!!.warn(message.toString())
  }

  override fun warn(message: CharSequence, cause: Throwable?) {
    logger!!.warn(message.toString(), cause)
  }

  override fun isErrorEnable(): Boolean {
    return logger!!.isErrorEnabled
  }

  override fun error(message: CharSequence) {
    logger!!.error(message.toString())
  }

  override fun error(message: CharSequence, cause: Throwable?) {
    logger!!.error(message.toString(), cause)
  }

  override fun isFatalEnable(): Boolean {
    return false
  }

  override fun fatal(charSequence: CharSequence?) {
    throw IllegalCallerException("This method is not supported")
  }

  override fun fatal(charSequence: CharSequence?, throwable: Throwable?) {
    throw IllegalCallerException("This method is not supported")
  }
}
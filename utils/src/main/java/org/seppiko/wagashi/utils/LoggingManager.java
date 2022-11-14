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

import org.seppiko.commons.logging.AbstractLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging manager
 *
 * @author Leonard Woo
 */
public record LoggingManager(Logger logger) implements AbstractLogging {

  public static LoggingManager getLogger(Class<?> clazz) {
    return getLogger(clazz.getName());
  }

  public static LoggingManager getLogger(String name) {
    return new LoggingManager(LoggerFactory.getLogger(name));
  }

  @Override
  public boolean isTraceEnable() {
    return logger.isTraceEnabled();
  }

  @Override
  public void trace(CharSequence message) {
    logger.trace(message.toString());
  }

  @Override
  public void trace(CharSequence message, Throwable cause) {
    logger.trace(message.toString(), cause);
  }

  @Override
  public boolean isDebugEnable() {
    return logger.isDebugEnabled();
  }

  @Override
  public void debug(CharSequence message) {
    logger.debug(message.toString());
  }

  @Override
  public void debug(CharSequence message, Throwable cause) {
    logger.debug(message.toString(), cause);
  }

  @Override
  public boolean isInfoEnable() {
    return logger.isInfoEnabled();
  }

  @Override
  public void info(CharSequence message) {
    logger.info(message.toString());
  }

  @Override
  public void info(CharSequence message, Throwable cause) {
    logger.info(message.toString(), cause);
  }

  @Override
  public boolean isWarnEnable() {
    return logger.isWarnEnabled();
  }

  @Override
  public void warn(CharSequence message) {
    logger.warn(message.toString());
  }

  @Override
  public void warn(CharSequence message, Throwable cause) {
    logger.warn(message.toString(), cause);
  }

  @Override
  public boolean isErrorEnable() {
    return logger.isErrorEnabled();
  }

  @Override
  public void error(CharSequence message) {
    logger.error(message.toString());
  }

  @Override
  public void error(CharSequence message, Throwable cause) {
    logger.error(message.toString(), cause);
  }

  @Override
  public boolean isFatalEnable() {
    return false;
  }

  @Override
  public void fatal(CharSequence charSequence) {
    throw new IllegalCallerException("This method is not supported");
  }

  @Override
  public void fatal(CharSequence charSequence, Throwable throwable) {
    throw new IllegalCallerException("This method is not supported");
  }

}

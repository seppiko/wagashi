package org.seppiko.wagashi.test;

import org.junit.jupiter.api.Test;
import org.seppiko.commons.logging.Logging;
import org.seppiko.commons.logging.LoggingFactory;
import org.seppiko.wagashi.commons.WagashiConfiguration;
import org.seppiko.wagashi.commons.utils.JsonUtil;
import org.seppiko.wagashi.commons.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Leonard Woo
 */
public class WagashiTest {
  private final Logging logger = LoggingFactory.getLogging(this.getClass());

  @Test
  public void configTest() {
    logger.info(JsonUtil.INSTANCE.toJson(WagashiConfiguration.INSTANCE.getJdbc()));
  }

  @Test
  public void jwtTest() {
    logger.info(JwtUtil.INSTANCE.generator("admin"));
  }

  @Test
  public void pwdTest() {
    logger.info(new BCryptPasswordEncoder().encode("admin123"));
  }
}

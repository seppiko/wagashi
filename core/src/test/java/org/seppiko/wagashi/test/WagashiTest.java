package org.seppiko.wagashi.test;

import org.junit.jupiter.api.Test;
import org.seppiko.commons.logging.Logging;
import org.seppiko.commons.logging.LoggingFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Leonard Woo
 */
public class WagashiTest {

  private final Logging log = LoggingFactory.getLogging(this.getClass());

  @Test
  public void pwdTest() {
    log.info(new BCryptPasswordEncoder().encode("admin123"));
  }
}

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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.time.Clock;
import java.time.Instant;
import org.seppiko.wagashi.commons.configurations.WagashiConfigure;
import org.seppiko.wagashi.commons.models.JwtEntity;

/**
 * @author Leonard Woo
 */
public class JwtUtil {

  private static final JwtEntity config = WagashiConfigure.getInstance().getJwtConfig();

  private static final Algorithm algorithm;
  private static final String issuer;
  private static final Integer expires;

  static {
    algorithm = Algorithm.HMAC384(config.secret());
    issuer = config.issuer();
    expires = config.expires() == null? 3600: config.expires();
  }

  public static String jwtGenerator(String username) throws IllegalCallerException {
    try {
      Instant now = Instant.now(Clock.systemUTC());
      return JWT.create().withIssuer(issuer)
          .withIssuedAt(now)
          .withExpiresAt(now.plusMillis(expires))
          .withSubject(username)
          .sign(algorithm);
    } catch (JWTCreationException ex) {
      // Invalid signing configuration / Couldn't convert Claims.
      throw new IllegalCallerException("");
    }
  }

  public static Boolean verify(String token) throws IllegalCallerException {
    try {
      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer(issuer)
          .build();
      DecodedJWT decodedJWT = verifier.verify(token);
      return !decodedJWT.getPayload().equals("");
    } catch (JWTVerificationException ex) {
      // Invalid signature/claims
      throw new IllegalCallerException("");
    }
  }
}

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

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

/**
 * Spring boot security config
 *
 * @author Leonard Woo
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfigure {
  @Bean
  @Order(1)
  @Throws(
    Exception::class
  )
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
    http.authorizeHttpRequests { requests ->
      requests
        .antMatchers("/", "/settings").permitAll() // authorization-free url
        .anyRequest().authenticated()
    }
      .formLogin { form: FormLoginConfigurer<HttpSecurity?> -> form.loginPage("/login").permitAll() } // login/sign in
      .logout { logout: LogoutConfigurer<HttpSecurity?> ->
        logout.logoutSuccessUrl("/").permitAll() } // logout/sign out
    //            .rememberMe().rememberMeParameter("rememberMe")
    return http.build()
  }

  @Bean
  @Order(2)
  @Throws(
    Exception::class
  )
  fun csrfFilterChain(http: HttpSecurity): SecurityFilterChain? {
    http
      .csrf()
      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF token with cookie
    //        .disable() // CSRF disable
    return http.build()
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder? {
    return BCryptPasswordEncoder()
  }

}
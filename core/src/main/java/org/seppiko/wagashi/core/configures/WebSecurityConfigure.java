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

package org.seppiko.wagashi.core.configures;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;


/**
 * Spring boot security config
 *
 * @author Leonard Woo
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigure {

  @Bean
  @Order(1)
  public SecurityFilterChain authorizeFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
    // authorization-free url list
    ArrayList<AntPathRequestMatcher> antRequestMatcherList = new ArrayList<>();
    antRequestMatcherList.add(new AntPathRequestMatcher("/"));
    antRequestMatcherList.add(new AntPathRequestMatcher("/settings"));

    http.authorizeHttpRequests((requests) -> requests
                    .requestMatchers(antRequestMatcherList.toArray(AntPathRequestMatcher[]::new)).permitAll()
                    .anyRequest().authenticated())
            .formLogin((form) -> form
                    .loginPage("/login")
                    .permitAll()) // login/sign in
            .logout((logout) -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
//                    .invalidateHttpSession(true)
                    .permitAll()) // logout/sign out
//            .rememberMe((remember) -> remember
//                .rememberMeServices(rememberMeServices))
    ;

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain csrfFilterChain(HttpSecurity http) throws Exception {
    http
//        .csrf(csrf -> {
//          csrf
////                  .ignoringRequestMatchers("/token")
//                  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF token with cookie
//          ;
//        })
        .csrf().disable() // CSRF disable
    ;
    return http.build();
  }

//  @Bean
//  @Order(3)
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//            .securityContext((securityContext) -> securityContext
//            .securityContextRepository(new RequestAttributeSecurityContextRepository()))
//            .sessionManagement(session -> session
//                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//            ;
//
//    return http.build();
//  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private final String REMEMBER_ME_KEY = "wagashi-spring-boot";

  @Bean
  public RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
    TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
    TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService, encodingAlgorithm);
    rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
    return rememberMe;
  }

}

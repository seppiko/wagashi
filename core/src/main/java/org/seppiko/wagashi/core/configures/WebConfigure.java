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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring boot web config
 *
 * @author Leonard Woo
 */
@Configuration
public class WebConfigure {

  /**
   * CORS config
   */
  @Bean
  public WebMvcConfigurer corsConfigure() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("*")
            .allowCredentials(true)
            .maxAge(3600);
      }
    };
  }

  /**
   * Security config
   */
//  @Bean
//  public WebMvcConfigurer securityConfigure() {
//    return new WebMvcConfigurer() {
//      @Override
//      public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login");
//      }
//    };
//  }
}

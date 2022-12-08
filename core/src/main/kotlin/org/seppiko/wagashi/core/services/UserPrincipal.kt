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
package org.seppiko.wagashi.core.services

import org.seppiko.wagashi.core.models.Role
import org.seppiko.wagashi.core.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * @author Leonard Woo
 */
class UserPrincipal(user: User?, roles: List<Role?>) : UserDetails {
  private val user: User?
  private val roles: List<Role?>

  init {
    this.user = user
    this.roles = roles
  }

  override fun getAuthorities(): Collection<GrantedAuthority?>? {
    val authList: ArrayList<GrantedAuthority?> = ArrayList();
    for (role: Role? in roles) {
      if (role != null) {
        authList.add(RolePrincipal(role))
      }
    }
    return authList
  }

  override fun getPassword(): String? {
    return user!!.password
  }

  override fun getUsername(): String? {
    return user!!.username
  }

  override fun isAccountNonExpired(): Boolean {
    return true
  }

  override fun isAccountNonLocked(): Boolean {
    return true
  }

  override fun isCredentialsNonExpired(): Boolean {
    return true
  }

  override fun isEnabled(): Boolean {
    return true
  }
}
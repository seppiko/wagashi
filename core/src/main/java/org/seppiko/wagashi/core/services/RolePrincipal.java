package org.seppiko.wagashi.core.services;

import org.seppiko.wagashi.core.models.Role;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Leonard Woo
 */
public class RolePrincipal implements GrantedAuthority {

  private Role role;

  public RolePrincipal(Role role) {
    this.role = role;
  }

  @Override
  public String getAuthority() {
    return role.name();
  }
}

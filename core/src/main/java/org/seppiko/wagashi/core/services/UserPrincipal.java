package org.seppiko.wagashi.core.services;

import org.seppiko.wagashi.core.models.Role;
import org.seppiko.wagashi.core.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Leonard Woo
 */
public class UserPrincipal implements UserDetails {

  private User user;
  private List<Role> roleList;

  public UserPrincipal(User user, List<Role> roleList) {
    this.user = user;
    this.roleList = roleList;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    ArrayList<RolePrincipal> rolePrincipals = new ArrayList<>();
    roleList.forEach(role -> {
      rolePrincipals.add(new RolePrincipal(role));
    });
    return rolePrincipals;
  }

  @Override
  public String getPassword() {
    return user.password();
  }

  @Override
  public String getUsername() {
    return user.username();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

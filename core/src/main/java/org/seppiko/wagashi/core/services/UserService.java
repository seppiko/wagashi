package org.seppiko.wagashi.core.services;

import org.seppiko.wagashi.core.mappers.RoleMapper;
import org.seppiko.wagashi.core.mappers.UserMapper;
import org.seppiko.wagashi.core.models.Role;
import org.seppiko.wagashi.core.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Leonard Woo
 */
@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserMapper umapper;

  @Autowired
  private RoleMapper rmapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = umapper.queryByUsername(username);
    List<Role> roles = rmapper.queryByUserID(user.id());
    return new UserPrincipal(user, roles);
  }

}

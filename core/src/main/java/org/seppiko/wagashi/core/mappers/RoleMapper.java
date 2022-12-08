package org.seppiko.wagashi.core.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.seppiko.wagashi.core.models.Role;

import java.util.List;

/**
 * @author Leonard Woo
 */
@Mapper
public interface RoleMapper {
  List<Role> queryByUserID(Long id);
}

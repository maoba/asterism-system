package com.maoba.service;
import java.util.List;
import java.util.Set;

import com.maoba.system.domain.RolePermissionEntity;
import com.maoba.system.domain.UserRoleEntity;
public interface RolePermissionService {
    
	/**
	 * 根据用户角色关系查询角色编码
	 * @param userRoles
	 * @return
	 */
	Set<String> queryRolePermission(List<UserRoleEntity> userRoles);
    
	/**
	 * 根据用户角色关系查询角色权限实体
	 * @param userRoles
	 * @return
	 */
	Set<RolePermissionEntity> queryRolePermissionEntity(List<UserRoleEntity> userRoles);
}

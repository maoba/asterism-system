package com.maoba.service;
import java.util.List;
import java.util.Set;

import com.maoba.facade.dto.RolePermissionDto;
import com.maoba.facade.dto.requestdto.RolePermissionRequest;
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

	/**
	 * 删除
	 * @param ids
	 */
	void deleteByRoleIds(Set<Long> ids);
    
	/**
	 * 根据权限id进行删除角色权限关系
	 * @param ids [权限id]
	 */
	void deleteByPermissionIds(Set<Long> ids);
    
	/**
	 * 查询某个角色已经绑定过的角色
	 * @param roleId
	 * @return
	 */
	List<RolePermissionDto> queryRolePermissionByRoleId(Long roleId);
    
	/**
	 * 保存角色权限
	 * @param request
	 */
	void saveRolePermission(RolePermissionRequest request);
}

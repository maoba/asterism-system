package com.maoba.service.impl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maoba.service.RolePermissionService;
import com.maoba.system.dao.RolePermissionEntityMapper;
import com.maoba.system.domain.RolePermissionEntity;
import com.maoba.system.domain.UserRoleEntity;

/**
 * @author kitty daddy
 *  角色权限服务
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService{

	@Autowired
	private RolePermissionEntityMapper rolePermissionMapper;
	
	@Override
	public Set<String> queryRolePermission(List<UserRoleEntity> userRoles) {
		Set<String> permissionCodes = null;
		if(CollectionUtils.isNotEmpty(userRoles)){
			permissionCodes = new HashSet<String>();
			
			for(UserRoleEntity userRoleEntity : userRoles){
				//根据角色id查看角色权限
				List<RolePermissionEntity> rolePermissions = rolePermissionMapper.queryRolePermissionByRoleId(userRoleEntity.getRoleId());
				
				//组装编码
				if(CollectionUtils.isNotEmpty(rolePermissions)){
					for(RolePermissionEntity rolerPermissionEntity : rolePermissions){
						permissionCodes.add(rolerPermissionEntity.getPermissionCode());
					}
				}
			}
		}
		return permissionCodes;
	}

	@Override
	public Set<RolePermissionEntity> queryRolePermissionEntity(List<UserRoleEntity> userRoles) {
		Set<RolePermissionEntity> rolePermissionEntities = null;
		
		if(CollectionUtils.isNotEmpty(userRoles)){
			rolePermissionEntities = new HashSet<RolePermissionEntity>();
			
			for(UserRoleEntity userRoleEntity : userRoles){
				//根据角色id查看角色权限
				List<RolePermissionEntity> rolePermissions = rolePermissionMapper.queryRolePermissionByRoleId(userRoleEntity.getRoleId());
				
				//重新组装实体类
				if(CollectionUtils.isNotEmpty(rolePermissions)){
					for(RolePermissionEntity rolerPermissionEntity : rolePermissions){
						rolePermissionEntities.add(rolerPermissionEntity);
					}
				}
			}
		}
		
		return rolePermissionEntities;
	}

	@Override
	public void deleteByRoleIds(Set<Long> ids) {
        if(CollectionUtils.isNotEmpty(ids)){
        	 for(Long id : ids){
        		 rolePermissionMapper.deleteByRoleId(id);
        	 }
        }		
	}

}

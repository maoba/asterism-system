package com.maoba.service.impl;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maoba.common.utils.IdSplitUtil;
import com.maoba.facade.convert.RolePermissionConvert;
import com.maoba.facade.dto.RolePermissionDto;
import com.maoba.facade.dto.requestdto.RolePermissionRequest;
import com.maoba.service.RolePermissionService;
import com.maoba.system.dao.PermissionEntityMapper;
import com.maoba.system.dao.RoleEntityMapper;
import com.maoba.system.dao.RolePermissionEntityMapper;
import com.maoba.system.domain.PermissionEntity;
import com.maoba.system.domain.RoleEntity;
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
	
	@Autowired
	private PermissionEntityMapper permissionMapper;
	
	@Autowired
	private RoleEntityMapper roleMapper;
	
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

	@Override
	public void deleteByPermissionIds(Set<Long> ids) {
         if(CollectionUtils.isNotEmpty(ids)){
        	 for(Long id : ids){
        		 rolePermissionMapper.deleteByPermissionId(id);
        	 }
         }		
	}

	@Override
	public List<RolePermissionDto> queryRolePermissionByRoleId(Long roleId) {
		List<RolePermissionEntity> entities = rolePermissionMapper.queryRolePermissionByRoleId(roleId);
		List<RolePermissionDto> rolePermissionDtos = RolePermissionConvert.convertEntity2Dto(entities);
		return rolePermissionDtos;
	}

	@Override
	public void saveRolePermission(RolePermissionRequest request) {
		//根据角色id删除角色权限关系
        rolePermissionMapper.deleteByRoleId(request.getRoleId());
        
        //重新插入角色权限关系
        Set<Long> permissionIds = IdSplitUtil.splitString2Long(request.getPermissionIds());
        if(CollectionUtils.isNotEmpty(permissionIds)){
        	for(Long permissionId : permissionIds){
        		 RolePermissionEntity entity = new RolePermissionEntity();
        		 entity.setCreateTime(new Date());
        		 PermissionEntity permissionEntity = permissionMapper.selectByPrimaryKey(permissionId);
        		 if(permissionEntity!=null){
        			 entity.setPermissionCode(permissionEntity.getPermissionCode());
        		 }
        		 entity.setPermissionId(permissionId);
        		 entity.setRoleId(request.getRoleId());
        		 RoleEntity roleEntity = roleMapper.selectByPrimaryKey(request.getRoleId());
        		 if(roleEntity!=null){
        			 entity.setRoleName(roleEntity.getRoleName());
        		 }
        		 rolePermissionMapper.insert(entity);
        	}
        }
	}

}

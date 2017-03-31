package com.maoba.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maoba.common.enums.StatusEnum;
import com.maoba.facade.convert.PermissionConvert;
import com.maoba.facade.dto.requestdto.PermissionRequest;
import com.maoba.facade.dto.responsedto.PermissionTreeResponse;
import com.maoba.service.PermissionService;
import com.maoba.service.RolePermissionService;
import com.maoba.service.UserRoleService;
import com.maoba.system.dao.PermissionEntityMapper;
import com.maoba.system.domain.PermissionEntity;
import com.maoba.system.domain.RolePermissionEntity;
import com.maoba.system.domain.UserRoleEntity;
import com.maoba.util.RedisUtil;
/**
 * @author kitty daddy
 * 权限服务
 */
@Service
public class PermissionServiceImpl implements PermissionService{

	@Autowired
    private PermissionEntityMapper permissionMapper;
	
	/**
	 * 用户角色服务
	 */
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * 
	 */
	@Autowired
	private RolePermissionService rolePermissionService;
	
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void savePermission(PermissionRequest request) {
		PermissionEntity entity = PermissionConvert.convertRequest2Entity(request);	
		if(entity!=null){
			entity.setStatus(StatusEnum.NORMAL.getValue());
			entity.setCreateTime(new Date());
			permissionMapper.insert(entity);
		}
	}

	@Override
	public List<PermissionTreeResponse> queryPermissionTree(Long userId, Long tenantId) {
		//TODO   此处代码可以进行优化处理（可以直接存入redis缓存中，之后作改造）
		//获取用户角色实体类
		List<UserRoleEntity> userRoleEntitys = userRoleService.queryUserRole(userId, tenantId);
		
		//查询获取角色权限实体类
		Set<RolePermissionEntity> rolePermissionEntitys = rolePermissionService.queryRolePermissionEntity(userRoleEntitys);

		//查询权限组装权限
        List<PermissionTreeResponse> response = buildPermissionTree(rolePermissionEntitys);		
		
		return response;
	}
    
	/**
	 * 组装权限树
	 * @param rolePermissionEntitys
	 * @return
	 */
	private List<PermissionTreeResponse> buildPermissionTree(Set<RolePermissionEntity> rolePermissionEntitys) {
		//获取最上层的权限
		Set<PermissionEntity> topPermissions = null;
		
		List<PermissionTreeResponse> responses = null;
		
		if(CollectionUtils.isNotEmpty(rolePermissionEntitys)){
		     topPermissions = new HashSet<PermissionEntity>();
		     	
		     for(RolePermissionEntity rolePermissionEntity : rolePermissionEntitys){
		    	   PermissionEntity permissionEntity = permissionMapper.selectByPrimaryKey(rolePermissionEntity.getPermissionId());
		    	    //获取最上级的树
		    	   if(permissionEntity.getParentId() == 0){
		    		    topPermissions.add(permissionEntity);
		    	   }
		     } 	
		}
		
		if(CollectionUtils.isNotEmpty(topPermissions)){
			responses = new ArrayList<PermissionTreeResponse>();
			//最上层的树 最终进行组装
			for(PermissionEntity topEntity : topPermissions){
				  List<PermissionEntity> childEntitys = permissionMapper.queryPermissionByParentId(topEntity.getId());
				  PermissionTreeResponse treeResponse = PermissionConvert.convertEntity2TreeResponse(topEntity,childEntitys);
				  responses.add(treeResponse);
			}
		}
		return responses;
	}
	
    
}

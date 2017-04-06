package com.maoba.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maoba.common.enums.StatusEnum;
import com.maoba.facade.convert.PermissionConvert;
import com.maoba.facade.dto.PermissionDto;
import com.maoba.facade.dto.PermissionTreeDto;
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
	 * 角色权限服务
	 */
	@Autowired
	private RolePermissionService rolePermissionService;
	
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void savePermission(PermissionRequest request) {
		if(request.getParentId()!=null){
			PermissionEntity entity = permissionMapper.selectByPrimaryKey(request.getParentId());
            if(entity!=null){
            	request.setParentName(entity.getModuleName());
            }
		}
		PermissionEntity entity = PermissionConvert.convertRequest2Entity(request);	
		if(entity!=null){
			entity.setStatus(StatusEnum.NORMAL.getValue());
			entity.setCreateTime(new Date());
			permissionMapper.insert(entity);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override//分业查询权限dto
	public PageInfo<PermissionDto> queryPermissionByPage(String name,Long tenantId, Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize, true, null, true);
		List<PermissionEntity> entitys = permissionMapper.queryPermissionByPage(name,tenantId);
		PageInfo pageInfo = new PageInfo(entitys);
		List<PermissionDto> permissionDtos = PermissionConvert.convertEntitys2Dtos(entitys);
		pageInfo.setList(permissionDtos);
		return pageInfo;
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
		Set<Long> permissionIdContainer = new HashSet<Long>();
		if(CollectionUtils.isNotEmpty(rolePermissionEntitys)){
		     topPermissions = new HashSet<PermissionEntity>();
		     
		     for(RolePermissionEntity rolePermissionEntity : rolePermissionEntitys){
		    	   PermissionEntity permissionEntity = permissionMapper.selectByPrimaryKey(rolePermissionEntity.getPermissionId());
		    	    //获取最上级的树
		    	   if(permissionEntity.getParentId() == 0){
		    		    topPermissions.add(permissionEntity);
		    	   }
		    	   
		    	   permissionIdContainer.add(rolePermissionEntity.getPermissionId());
		     } 	
		}
		
		if(CollectionUtils.isNotEmpty(topPermissions)){
			responses = new ArrayList<PermissionTreeResponse>();
			//最上层的树 最终进行组装
			for(PermissionEntity topEntity : topPermissions){
				  List<PermissionEntity> childEntitys = permissionMapper.queryPermissionByParentId(topEntity.getId());
				  //过滤掉权限中不存在的权限实体
				  List<PermissionEntity> filterEntitys = this.filterPermissionEntity(childEntitys,permissionIdContainer);
				  
				  PermissionTreeResponse treeResponse = PermissionConvert.convertEntity2TreeResponse(topEntity,filterEntitys);
				  responses.add(treeResponse);
			}
		}
		return responses;
	}
    
	/**
	 * 过滤掉不在权限中的菜单
	 * @param childEntitys
	 * @param permissionIdContainer
	 * @return
	 */
	private List<PermissionEntity> filterPermissionEntity(List<PermissionEntity> childEntitys, Set<Long> permissionIdContainer) {
        List<PermissionEntity> finalPermissionEntities = null;
		if(CollectionUtils.isNotEmpty(childEntitys)){
			finalPermissionEntities = new ArrayList<PermissionEntity>();
        	for(PermissionEntity permissionEntity : childEntitys){
        		if(permissionIdContainer.contains(permissionEntity.getId())){
        			finalPermissionEntities.add(permissionEntity);
        		}
        	}
        }
		return finalPermissionEntities;
	}

	@Override
	public List<PermissionDto> queryCatalogPermissions() {
		List<PermissionEntity> entitys = permissionMapper.queryCatalogPermission();
		List<PermissionDto> permissionDtos = PermissionConvert.convertEntitys2Dtos(entitys);
		return permissionDtos;
	}

	@Override
	public void delete(Set<Long> ids) {
         if(CollectionUtils.isNotEmpty(ids)){
        	 for(Long id : ids){
        		 permissionMapper.deleteByPrimaryKey(id);
        	 }
         }		
	}

	@Override
	public void updatePermission(PermissionRequest request) {
		PermissionEntity oldParentEntity = permissionMapper.selectByPrimaryKey(request.getParentId());
        PermissionEntity oldEntity = permissionMapper.selectByPrimaryKey(request.getId());   
		oldEntity = PermissionConvert.convertRequest2Entity(oldEntity,request);
		if(oldParentEntity!=null){
			oldEntity.setParentName(oldParentEntity.getModuleName());
		}
		oldEntity.setUpdateTime(new Date());
        permissionMapper.updateByPrimaryKey(oldEntity);
	}

	@Override
	public List<PermissionTreeDto> queryPermissionByTenantId(Long tenantId) {
		List<PermissionEntity> entities = permissionMapper.queryPermissionByTenantId(tenantId);
		List<PermissionTreeDto> treeDtos = PermissionConvert.convertEntity2TreeDto(entities);
		return treeDtos;
	}
}

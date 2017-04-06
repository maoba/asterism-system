package com.maoba.service.impl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maoba.common.utils.IdSplitUtil;
import com.maoba.facade.convert.UserRoleConvert;
import com.maoba.facade.dto.UserRoleDto;
import com.maoba.facade.dto.requestdto.UserRoleRequest;
import com.maoba.service.UserRoleService;
import com.maoba.system.dao.RoleEntityMapper;
import com.maoba.system.dao.UserMapper;
import com.maoba.system.dao.UserRoleEntityMapper;
import com.maoba.system.domain.RoleEntity;
import com.maoba.system.domain.UserEntity;
import com.maoba.system.domain.UserRoleEntity;
/**
 * @author kitty daddy
 * 用户角色服务
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
    private UserRoleEntityMapper userRoleMapper;
	
	@Autowired
	private RoleEntityMapper roleMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public Set<String> queryRoleCodes(long userId, long tenantId) {
		Set<String> roleCodes =  new HashSet<String>();
		//获取用户角色关系实体类
		List<UserRoleEntity> userRoleEntitys = userRoleMapper.queryRole(userId,tenantId);
		if(CollectionUtils.isNotEmpty(userRoleEntitys)){
			for(UserRoleEntity entity : userRoleEntitys){
				roleCodes.add(entity.getRoleCode());
			}
		}
		return roleCodes;
	}

	@Override
	public List<UserRoleEntity> queryUserRole(long userId, long tenantId) {
		return userRoleMapper.queryRole(userId, tenantId);
	}

	@Override//根据角色id进行删除用户角色关系
	public void deleteByRoleIds(Set<Long> ids) {
		if(CollectionUtils.isNotEmpty(ids)){
			for(Long id : ids){
				userRoleMapper.deleteByRoleId(id);
			}
		}
	}

	@Override//根据用户id进行删除用户角色关系
	public void deleteByUserId(Set<Long> ids) {
         if(CollectionUtils.isNotEmpty(ids)){
        	 for(Long id : ids){
        		 userRoleMapper.deleteByUserId(id);
        	 }
         }		
	}

	@Override
	public List<UserRoleDto> queryBandingRoles(Long userId,Long tenantId) {
		List<UserRoleEntity> entities = userRoleMapper.queryRole(userId, tenantId);
		List<UserRoleDto> userRoleDtos = UserRoleConvert.convertEntity2Dto(entities);
		return userRoleDtos;
	}

	@Override
	@Transactional
	public void saveUserRole(UserRoleRequest request) {
		  //首先清空原先的关系
          userRoleMapper.deleteByUserId(request.getUserId());
          
          //插入新的关系
          Set<Long> roleIds = IdSplitUtil.splitString2Long(request.getRoleIds());
          if(CollectionUtils.isNotEmpty(roleIds)){
        	  for(Long roleId : roleIds){
        		  UserRoleEntity entity = new UserRoleEntity();
        		  /**
        		   * 获取role信息
        		   */
        		  RoleEntity roleEntity = roleMapper.selectByPrimaryKey(roleId);
        		  if(roleEntity!=null){
        			  entity.setRoleCode(roleEntity.getRoleCode());//设置角色编码
        			  entity.setRoleName(roleEntity.getRoleName());//设置角色名称
        		  }
        		  entity.setRoleId(roleId);//设置角色id
        		  entity.setTenantId(request.getTenantId());//设置租户id
        		  entity.setUserId(request.getUserId());//设置用户id
        		  UserEntity userEntity = userMapper.selectByPrimaryKey(request.getUserId());
        		  if(userEntity!=null){
        			  entity.setUserName(userEntity.getUserName());//设置用户名称
        		  }
        		  userRoleMapper.insert(entity);
        	  }
          }
          
	}
}

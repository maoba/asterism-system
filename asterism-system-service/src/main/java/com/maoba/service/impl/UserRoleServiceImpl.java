package com.maoba.service.impl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maoba.service.UserRoleService;
import com.maoba.system.dao.UserRoleEntityMapper;
import com.maoba.system.domain.UserRoleEntity;

/**
 * @author kitty daddy
 * 用户角色服务
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
    private UserRoleEntityMapper userRoleMapper;

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
}

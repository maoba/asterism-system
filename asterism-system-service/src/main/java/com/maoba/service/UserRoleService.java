package com.maoba.service;
import java.util.List;
import java.util.Set;

import com.maoba.facade.dto.UserRoleDto;
import com.maoba.facade.dto.requestdto.UserRoleRequest;
import com.maoba.system.domain.UserRoleEntity;
public interface UserRoleService {
    /**
     * 根据用户id以及租户Id获取角色编码
     * @param userId [用户Id]
     * @param tenantId [租户Id]
     * @return
     */
	Set<String> queryRoleCodes(long userId, long tenantId);
    
	/**
	 * 查询用户角色关系
	 * @param userId [用户Id]
     * @param tenantId [租户Id]
	 * @return
	 */
	List<UserRoleEntity> queryUserRole(long userId, long tenantId);
    
	/**
	 * 删除角色id
	 * @param ids
	 */
	void deleteByRoleIds(Set<Long> ids);
    
	/**
	 * 根据用户Id进行删除用户角色关系
	 * @param ids
	 */
	void deleteByUserId(Set<Long> ids);
    
	/**
	 * 根据userId查询用户角色关系
	 * @param userId [用户id]
	 * @return
	 */
	List<UserRoleDto> queryBandingRoles(Long userId,Long tenantId);
    
	/**
	 * 保存用户角色
	 * @param request
	 */
	void saveUserRole(UserRoleRequest request);

}

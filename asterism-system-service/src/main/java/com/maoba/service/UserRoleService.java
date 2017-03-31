package com.maoba.service;
import java.util.List;
import java.util.Set;
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

}

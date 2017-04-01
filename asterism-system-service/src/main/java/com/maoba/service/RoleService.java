package com.maoba.service;

import com.github.pagehelper.PageInfo;
import com.maoba.facade.dto.RoleDto;
import com.maoba.facade.dto.requestdto.RoleRequest;

public interface RoleService {
     
	/**
	 * 保存角色
	 * @param request
	 */
	void saveRole(RoleRequest request);
    
	/**
	 * 分页查询
	 * @param name 角色名称
	 * @param tenantId  租户id
	 * @param pageIndex  当前页码
	 * @param pageSize  一页上面的总记录数
	 * @return
	 */
	PageInfo<RoleDto> queryRolesByPage(String name, Long tenantId, Integer pageIndex, Integer pageSize);

}

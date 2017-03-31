package com.maoba.service;

import com.maoba.facade.dto.requestdto.RoleRequest;

public interface RoleService {
     
	/**
	 * 保存角色
	 * @param request
	 */
	void saveRole(RoleRequest request);

}

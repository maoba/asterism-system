package com.maoba.service;

import java.util.List;

import com.maoba.facade.dto.requestdto.PermissionRequest;
import com.maoba.facade.dto.responsedto.PermissionTreeResponse;

public interface PermissionService {
	
    /**
     * 权限保存
     * @param request
     */
	void savePermission(PermissionRequest request);
    
	/**
	 * 根据用户的Id以及租户的id查询权限树
	 * @param userId [用户id]
	 * @param tenantId [租户id]
	 * @return
	 */
	List<PermissionTreeResponse> queryPermissionTree(Long userId, Long tenantId);

}

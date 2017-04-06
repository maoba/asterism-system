package com.maoba.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.maoba.facade.dto.PermissionDto;
import com.maoba.facade.dto.PermissionTreeDto;
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

	
	/**
	 * 分业查询权限
	 * @param name  [权限名称]
	 * @param tenantId [租户id]
	 * @param pageIndex [页码]
	 * @param pageSize  [一页总条数]
	 * @return
	 */
	PageInfo<PermissionDto> queryPermissionByPage(String name, Long tenantId, Integer pageIndex, Integer pageSize);
    
	/**
	 * 查询所有的父级目录
	 * @return
	 */
	List<PermissionDto> queryCatalogPermissions();
    
	/**
	 * 删除权限
	 * @param ids
	 */
	void delete(Set<Long> ids);
    
	/**
	 * 更新权限
	 * @param request
	 */
	void updatePermission(PermissionRequest request);
    
	/**
	 * 根据租户的Id查询权限
	 * @param tenantId[租户id]
	 * @return
	 */
	List<PermissionTreeDto> queryPermissionByTenantId(Long tenantId);

}

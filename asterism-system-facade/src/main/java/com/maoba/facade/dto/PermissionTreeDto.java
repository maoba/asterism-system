package com.maoba.facade.dto;
/**
 * @author kitty daddy
 * 前端分配权限时需要的树形容器
 */
public class PermissionTreeDto {
    /**
     * 权限id
     */
	private String id;
	
	/**
	 * 权限父Id,如果没有父级的时候为 '#'
	 */
	private String parentId;
	
	/**
	 * 权限名称
	 */
	private String permissionName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
}

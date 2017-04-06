package com.maoba.facade.dto.requestdto;
/**
 * @author kitty daddy
 * 用户角色请求头
 */
public class RolePermissionRequest {
    /**
     * 角色id 
     */
	 private Long roleId;
     
	 /**
	  * 权限Id
	  */
     private String permissionIds;

	 public Long getRoleId() {
		return roleId;
	 }

	 public void setRoleId(Long roleId) {
		this.roleId = roleId;
	 }

	 public String getPermissionIds() {
		return permissionIds;
	 }

	 public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	 }
}

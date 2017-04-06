package com.maoba.facade.dto.requestdto;
/**
 * @author kitty daddy
 * 用户角色请求容器
 */
public class UserRoleRequest {
	/**
	 * 用户id
	 */
    private Long userId;
    
    /**
     * 角色id
     */
    private String roleIds;
    
    /**
     * 租户id
     */
    private Long tenantId;

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
}

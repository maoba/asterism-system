package com.maoba.facade.dto.requestdto;
import java.util.Date;
/**
 * @author kitty daddy
 * 权限请求
 */
public class PermissionRequest {
	/**
	 * id
	 */
	private Long id;
	
    /**
     * 模块名称
     */
    private String moduleName;
    
    /**
     * 模块编码
     */
    private String moduleCode;
    
    /**
     * 权限编码
     */
    private String permissionCode;
    
    /**
     * 权限ur
     */
    private String permissionUrl;

    
    /**
     * 0:目录 1:资源
     */
    private Integer permissionType;
    
    /**
     * 0:pc权限 1:手机权限
     */
    private Integer terminalType;
    
    /**
     * 租户的id
     */
    private Long tenantId;
    
    /**
     * 父节点
     */
    private Long parentId;
    
    /**
     * 父节点名称
     */
    private String parentName;
    
    /**
     * 状态 -1:删除 0:失效 1:生效
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 权限图标
     */
    private String permissionICO;
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermissionICO() {
		return permissionICO;
	}

	public void setPermissionICO(String permissionICO) {
		this.permissionICO = permissionICO;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}

	public Integer getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(Integer permissionType) {
		this.permissionType = permissionType;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

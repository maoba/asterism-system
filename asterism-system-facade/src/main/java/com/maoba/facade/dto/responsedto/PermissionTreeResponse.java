package com.maoba.facade.dto.responsedto;

import java.util.List;

/**
 * @author kitty daddy
 * 权限树返回[权限树目前只会有两层]
 */
public class PermissionTreeResponse {
     /**
      * 权限名称
      */
	 private String permissionName;
     
	 /**
	  * 权限图标
	  */
     private String permissionICO;
     
     /**
      * 权限对应的资源url
      */
     private String permissionUrl;
     
     /**
      * 子目录
      */
     private List<PermissionTreeResponse> child;
     

	 public String getPermissionName() {
		return permissionName;
	 }

	 public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	 }

	 public String getPermissionICO() {
		return permissionICO;
	 }

	 public void setPermissionICO(String permissionICO) {
		this.permissionICO = permissionICO;
	 }

	 public String getPermissionUrl() {
		return permissionUrl;
	 }

	 public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	 }

	 public List<PermissionTreeResponse> getChild() {
		return child;
	 }

	 public void setChild(List<PermissionTreeResponse> child) {
		this.child = child;
	 }
}

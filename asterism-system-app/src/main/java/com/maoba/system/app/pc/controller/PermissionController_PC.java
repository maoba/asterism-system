package com.maoba.system.app.pc.controller;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.maoba.annotation.CurrentUser;
import com.maoba.annotation.CurrentUserInfo;
import com.maoba.common.enums.TerminalTypeEnum;
import com.maoba.facade.dto.PermissionDto;
import com.maoba.facade.dto.RoleDto;
import com.maoba.facade.dto.requestdto.PermissionRequest;
import com.maoba.facade.dto.responsedto.BaseResponse;
import com.maoba.facade.dto.responsedto.PageResponse;
import com.maoba.facade.dto.responsedto.PermissionTreeResponse;
import com.maoba.service.PermissionService;
/**
 * @author kitty daddy
 * 权限控制
 */
@RestController
@RequestMapping(value="/pc/permission")
public class PermissionController_PC {
    @Autowired
    private PermissionService permissionService;
    
    /**
     * 分页查询
     * @param name 模块名称
     * @param pageIndex 当前页码
     * @param pageSize 一页数量
     * @param currentUser 
     * @return
     */
    @SuppressWarnings("static-access")
	@RequestMapping(method=RequestMethod.GET,value="/queryPermissions")
    @ResponseBody
    public PageResponse queryRolesByPage(
		@RequestParam(value="name", required=false) String name,
        @RequestParam(value="pageIndex", required=false,defaultValue="0") Integer pageIndex,
        @RequestParam(value="pageSize", required=false,defaultValue="0") Integer pageSize,@CurrentUser CurrentUserInfo currentUser){
    	PageResponse pageResponse = new PageResponse();
    	
    	//根据模块名称，租户的id查询角色
    	PageInfo<PermissionDto> permissionDtos = permissionService.queryPermissionByPage(name,currentUser.getTenantId(),pageIndex,pageSize);
    	
	    return pageResponse.getSuccessPage(permissionDtos);
    }
    
    /**
     * 查找所有的目录
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryNormalCatalogs")
    @ResponseBody
    public BaseResponse queryNormalCatalogs(){
    	BaseResponse response = new BaseResponse();
    	List<PermissionDto> catalogPermissionDtos = permissionService.queryCatalogPermissions();
    	return BaseResponse.getSuccessResponse(new Date(), catalogPermissionDtos);
    }
    
    
    /**
     * 保存权限信息
     * @param request
     * @param currentUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save")
	@ResponseBody
	public BaseResponse savePermission(PermissionRequest request,@CurrentUser CurrentUserInfo currentUser){
    	//设置终端类型
    	request.setTerminalType(TerminalTypeEnum.TERMINAL_PC.getValue());
    	//设置租户Id
    	request.setTenantId(currentUser.getTenantId());
    	permissionService.savePermission(request);
        return BaseResponse.getSuccessResponse();
    }
    
    /**
     * 根据当前的用户获取该用户的权限树
     * @param currentUserInfo
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/queryPermissionTree",produces = "application/json")
    @ResponseBody
    public BaseResponse queryPermissionTree(@CurrentUser CurrentUserInfo currentUserInfo){
    	//根据用户的Id以及租户的id查询权限信息
    	List<PermissionTreeResponse> response = permissionService.queryPermissionTree(currentUserInfo.getUserId(),currentUserInfo.getTenantId());
    	return BaseResponse.getSuccessResponse(new Date(), response);
    }
}

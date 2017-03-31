package com.maoba.system.app.pc.controller;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maoba.annotation.CurrentUser;
import com.maoba.annotation.CurrentUserInfo;
import com.maoba.common.enums.TerminalTypeEnum;
import com.maoba.facade.dto.requestdto.PermissionRequest;
import com.maoba.facade.dto.responsedto.BaseResponse;
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

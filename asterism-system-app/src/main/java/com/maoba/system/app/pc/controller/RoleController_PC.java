package com.maoba.system.app.pc.controller;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maoba.annotation.CurrentUser;
import com.maoba.annotation.CurrentUserInfo;
import com.maoba.facade.dto.requestdto.RoleRequest;
import com.maoba.facade.dto.responsedto.BaseResponse;
import com.maoba.service.RoleService;
/**
 * @author kitty daddy
 *  pc端角色管理controller
 */
@RestController
@RequestMapping(value="/pc/role")
public class RoleController_PC {
       @Autowired
   	   private RoleService roleService;
   
	   /**
	    * 保存角色
	    * @param request
	    * @return
	    */
	   @RequestMapping(method = RequestMethod.POST, value = "/save")
	   @ResponseBody
	   public BaseResponse saveRole(RoleRequest request,@CurrentUser CurrentUserInfo currentUser){
		   //设置租户id
		   request.setTenantId(currentUser.getTenantId());
		   //保存角色
		   roleService.saveRole(request);
	   	   return BaseResponse.getSuccessResponse(new Date());
	   }
}

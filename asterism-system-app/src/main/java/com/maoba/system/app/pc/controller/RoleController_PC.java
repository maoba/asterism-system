package com.maoba.system.app.pc.controller;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.maoba.annotation.CurrentUser;
import com.maoba.annotation.CurrentUserInfo;
import com.maoba.facade.dto.RoleDto;
import com.maoba.facade.dto.UserDto;
import com.maoba.facade.dto.requestdto.RoleRequest;
import com.maoba.facade.dto.responsedto.BaseResponse;
import com.maoba.facade.dto.responsedto.PageResponse;
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
	     * 分页查询
	     * @param name 用户名称
	     * @param pageIndex 当前页码
	     * @param pageSize 一页数量
	     * @param currentUser 
	     * @return
	     */
	    @SuppressWarnings("static-access")
		@RequestMapping(method=RequestMethod.GET,value="/queryRoles")
	    @ResponseBody
	    public PageResponse queryRolesByPage(
			@RequestParam(value="name", required=false) String name,
            @RequestParam(value="pageIndex", required=false,defaultValue="0") Integer pageIndex,
            @RequestParam(value="pageSize", required=false,defaultValue="0") Integer pageSize,@CurrentUser CurrentUserInfo currentUser){
	    	PageResponse pageResponse = new PageResponse();
	    	//根据名称，租户的id查询角色
	    	PageInfo<RoleDto> roleDtos = roleService.queryRolesByPage(name,currentUser.getTenantId(),pageIndex,pageSize);
		    return pageResponse.getSuccessPage(roleDtos);
	    }
   
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

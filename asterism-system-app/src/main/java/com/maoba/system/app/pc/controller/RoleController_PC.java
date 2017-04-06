package com.maoba.system.app.pc.controller;
import java.util.Date;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.maoba.annotation.CurrentUser;
import com.maoba.annotation.CurrentUserInfo;
import com.maoba.common.utils.IdSplitUtil;
import com.maoba.facade.dto.RoleDto;
import com.maoba.facade.dto.UserRoleDto;
import com.maoba.facade.dto.requestdto.RoleRequest;
import com.maoba.facade.dto.requestdto.UserRoleRequest;
import com.maoba.facade.dto.responsedto.BaseResponse;
import com.maoba.facade.dto.responsedto.PageResponse;
import com.maoba.service.RolePermissionService;
import com.maoba.service.RoleService;
import com.maoba.service.UserRoleService;
/**
 * @author kitty daddy
 *  pc端角色管理controller
 */
@RestController
@RequestMapping(value="/pc/role")
public class RoleController_PC {
	  /**
	   * 角色服务
	   */
       @Autowired
   	   private RoleService roleService;
       
       /**
        * 用户角色服务
        */
       @Autowired
       private UserRoleService userRoleService;
       
       /**
        * 角色权限服务
        */
       @Autowired
       private RolePermissionService rolePermissionService;
       
       
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
		 * 后台进行删除
		 * @param ids
		 * @return
		 */
		@RequestMapping(method=RequestMethod.GET,value="/delete")
		@RequiresAuthentication//表示删除
		public BaseResponse delete(@RequestParam(value="ids") String ids){
			 //删除角色
			roleService.delete(IdSplitUtil.splitString2Long(ids));
			
			 //删除角色用户关系
			userRoleService.deleteByRoleIds(IdSplitUtil.splitString2Long(ids));
			
			 //删除角色权限关系
            rolePermissionService.deleteByRoleIds(IdSplitUtil.splitString2Long(ids));
            
			return BaseResponse.getSuccessResponse(new Date());
		}
	    
		/**
		 * 获取当前租户之下的所有的角色
		 * @param currentUserInfo
		 * @return
		 */
		@RequestMapping(method=RequestMethod.GET,value="/queryCurrentTenantRoles")
		@RequiresAuthentication
		public BaseResponse queryCurrentTenantRoles(@CurrentUser CurrentUserInfo currentUserInfo){
			 List<RoleDto> roleDtos = roleService.queryRolesByTenantId(currentUserInfo.getTenantId());
			 return BaseResponse.getSuccessResponse(new Date(), roleDtos);
		}
	    
		/**
		 * 根据用户id查询绑定的角色
		 * @param userId [用户Id]
		 * @return
		 */
		@RequestMapping(method=RequestMethod.GET,value="/queryBandingRoles")
		@RequiresAuthentication
		public BaseResponse queryBandingRoles(@RequestParam(value="userId") Long userId,@CurrentUser CurrentUserInfo currentUserInfo){
			 List<UserRoleDto> userRoleDtos = userRoleService.queryBandingRoles(userId,currentUserInfo.getTenantId());
			 return BaseResponse.getSuccessResponse(new Date(), userRoleDtos);
		}
		
		/**
		 * 保存用户角色
		 */
		@RequestMapping(method=RequestMethod.POST,value="/saveUserRole")
		@RequiresAuthentication
		public BaseResponse saveUserRole(UserRoleRequest request,@CurrentUser CurrentUserInfo currentUserInfo){
			request.setTenantId(currentUserInfo.getTenantId());
			userRoleService.saveUserRole(request);
			return BaseResponse.getSuccessResponse(new Date());
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
	   
	   /**
	    * 更新角色信息
	    * @param request
	    * @return
	    */
	   @RequestMapping(method = RequestMethod.POST, value = "/update")
	   public BaseResponse updateRole(RoleRequest request){
		    roleService.update(request);
		    return BaseResponse.getSuccessResponse(new Date());
	   }
}

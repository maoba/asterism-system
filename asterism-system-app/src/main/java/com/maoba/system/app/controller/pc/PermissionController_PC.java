package com.maoba.system.app.controller.pc;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.maoba.annotation.CurrentUser;
import com.maoba.annotation.CurrentUserInfo;
import com.maoba.common.enums.TerminalTypeEnum;
import com.maoba.common.utils.IdSplitUtil;
import com.maoba.facade.dto.PermissionDto;
import com.maoba.facade.dto.PermissionTreeDto;
import com.maoba.facade.dto.RolePermissionDto;
import com.maoba.facade.dto.requestdto.PermissionRequest;
import com.maoba.facade.dto.requestdto.RolePermissionRequest;
import com.maoba.facade.dto.responsedto.BaseResponse;
import com.maoba.facade.dto.responsedto.PageResponse;
import com.maoba.facade.dto.responsedto.PermissionTreeResponse;
import com.maoba.service.PermissionService;
import com.maoba.service.RolePermissionService;
/**
 * @author kitty daddy
 * 权限控制
 */
@RestController
@RequestMapping(value="/pc/permission")
public class PermissionController_PC {
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private RolePermissionService rolePermissionService;
    
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
    public PageResponse queryPermissionsByPage(
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
    	List<PermissionDto> catalogPermissionDtos = permissionService.queryCatalogPermissions();
    	return BaseResponse.getSuccessResponse(new Date(), catalogPermissionDtos);
    }
    
    /**
     * 查询当前租户下的所有的权限
     * @param currentUserInfo
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryCurrentTenantPermissions")
    @ResponseBody
    public BaseResponse queryPermissions(@CurrentUser CurrentUserInfo currentUserInfo){
    	 List<PermissionTreeDto> permissionTreeDtos = permissionService.queryPermissionByTenantId(currentUserInfo.getTenantId());
    	 return BaseResponse.getSuccessResponse(new Date(), permissionTreeDtos);
    }
    
    
    /**
     * 保存权限信息
     * @param request
     * @param currentUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = "application/json")
	@ResponseBody
	public BaseResponse savePermission(@RequestBody PermissionRequest request,@CurrentUser CurrentUserInfo currentUser){
    	//设置终端类型
    	request.setTerminalType(TerminalTypeEnum.TERMINAL_PC.getValue());
    	//设置租户Id
    	request.setTenantId(currentUser.getTenantId());
    	permissionService.savePermission(request);
        return BaseResponse.getSuccessResponse(new Date());
    }
    
    /**
     * 保存角色权限关系
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="/saveRolePermission", consumes = "application/json")
    @ResponseBody
    public BaseResponse saveRolePermission(@RequestBody RolePermissionRequest request){
    	rolePermissionService.saveRolePermission(request);
    	return BaseResponse.getSuccessResponse(new Date());
    }
    
    
    /**
     * 对权限进行更新
     * @return
     */
    @RequestMapping(method = RequestMethod.POST , value = "update", consumes = "application/json")
    @ResponseBody
    public BaseResponse updatePermission(@RequestBody PermissionRequest request,@CurrentUser CurrentUserInfo currentUserInfo){
    	request.setTenantId(currentUserInfo.getTenantId());
    	permissionService.updatePermission(request);
    	return BaseResponse.getSuccessResponse(new Date());
    }
    
    
    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "delete")
    public BaseResponse deletePermission(@RequestParam(value="ids") String ids){
    	//删除权限
    	permissionService.delete(IdSplitUtil.splitString2Long(ids));
    	//删除角色权限关系
    	rolePermissionService.deleteByPermissionIds(IdSplitUtil.splitString2Long(ids));
    	
    	return BaseResponse.getSuccessResponse(new Date());
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
    
    /**
     * 查询某个角色已经绑定过的角色
     * @param roleId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/queryBandingPermissions",produces = "application/json")
    @ResponseBody
    public BaseResponse queryBandingPermissions(@RequestParam(value="roleId") Long roleId){
    	List<RolePermissionDto> rolePermissionDtos = rolePermissionService.queryRolePermissionByRoleId(roleId);
    	List<Long> ids = new ArrayList<Long>();
    	if(CollectionUtils.isNotEmpty(rolePermissionDtos)){
    		for(RolePermissionDto dto : rolePermissionDtos){
    			ids.add(dto.getPermissionId());
    		}
    	}
    	return BaseResponse.getSuccessResponse(new Date(), ids);
    }
}

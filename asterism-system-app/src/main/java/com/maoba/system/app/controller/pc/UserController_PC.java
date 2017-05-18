package com.maoba.system.app.controller.pc;
import java.net.URLDecoder;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
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
import com.maoba.common.enums.CheckPasswordEnum;
import com.maoba.common.enums.EncryptionEnum;
import com.maoba.common.enums.LoginTypeEnum;
import com.maoba.common.enums.TerminalTypeEnum;
import com.maoba.common.utils.IdSplitUtil;
import com.maoba.facade.dto.UserDto;
import com.maoba.facade.dto.requestdto.PasswordRequest;
import com.maoba.facade.dto.requestdto.UserLoginRequest;
import com.maoba.facade.dto.requestdto.UserRequest;
import com.maoba.facade.dto.responsedto.BaseResponse;
import com.maoba.facade.dto.responsedto.PageResponse;
import com.maoba.facade.dto.responsedto.UserResponse;
import com.maoba.service.SecurityService;
import com.maoba.service.UserRoleService;
import com.maoba.service.UserService;
import com.maoba.util.PublicKeyMap;
import com.maoba.util.RSAUtils;
import com.maoba.util.RedisUtil;
/**
 * @author kitty daddy
 * pc端controller
 */
@RestController
@RequestMapping(value="/pc/user")
public class UserController_PC {
	    @Autowired
        private UserService userService;
	    
	    @Autowired
	    private UserRoleService userRoleService;
	    
	    @Autowired
	    private SecurityService securityService;
	  
	    @Autowired
	    private RedisUtil redisUtil;
	    
	    @Autowired
	    private HttpServletRequest httpRequest;
	    
	    /**
	     * 分页查询
	     * @param name 用户名称
	     * @param pageIndex 当前页码
	     * @param pageSize 一页数量
	     * @param currentUser 
	     * @return
	     */
	    @SuppressWarnings("static-access")
		@RequestMapping(method=RequestMethod.GET,value="/queryUsers")
	    @ResponseBody
	    public PageResponse queryUsersByPage(
			  @RequestParam(value="name", required=false) String name,
			  @RequestParam(value="status",required=false) Integer status,
              @RequestParam(value="pageIndex", required=false,defaultValue="0") Integer pageIndex,
              @RequestParam(value="pageSize", required=false,defaultValue="0") Integer pageSize,@CurrentUser CurrentUserInfo currentUser){
	    	PageResponse pageResponse = new PageResponse();
	    	
	    	//根据名称以及租户id进行查询
	    	PageInfo<UserDto> users = userService.queryUsersByPage(name,status,currentUser.getTenantId(),pageIndex,pageSize);
		    return pageResponse.getSuccessPage(users);
	    }
	  
	    /**
	     * 获取用的详情信息
	     * @param currentUserInfo
	     * @return
	     */
	    @RequiresAuthentication
		@RequestMapping(value="queryCurrentUserInfo",method=RequestMethod.GET)
		@ResponseBody
		public UserResponse queryCurrentUserResponse(@CurrentUser CurrentUserInfo currentUserInfo){
	    	
	    	UserResponse response = userService.queryUserById(currentUserInfo.getUserId());
			return response;
		}
	    
	    /**
	     * 保存用户
	     * @param request
	     * @return
	     */
	    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = "application/json")
	    @ResponseBody
	    public BaseResponse saveUser(@RequestBody UserRequest request,@CurrentUser CurrentUserInfo currentUserInfo){
	    	//获取相关的盐
	    	RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		    String salt = randomNumberGenerator.nextBytes().toHex();
		    request.setSalt(salt);
            request.setTerminalType(TerminalTypeEnum.TERMINAL_PC.getValue());
            
		    //设置租户 id
		    request.setTenantId(currentUserInfo.getTenantId());
		    Integer loginType = request.getLoginType();
		    
		    //邮箱方式注册设置加密密码
		    if(LoginTypeEnum.SYSTEM_EMAIL_LOGIN.getType() == loginType){
		    	request.setUserPwd(this.getEncryptPassword(salt, request.getUserPwd(), null, request.getEmail()));
		    	
		    //手机方式注册设置加密密码
		    }else if(LoginTypeEnum.SYSTEM_CELLPHONE_LOGIN.getType() == loginType){
		    	request.setUserPwd(this.getEncryptPassword(salt, request.getUserPwd(), null, request.getCellPhoneNum()));
		    }
		    
		    request.setCreateTime(new Date());
		    //保存入库
	    	userService.saveUser(request);
	    	return BaseResponse.getSuccessResponse(new Date());
	    }
	    
	    /**
	     * 更新用户信息
	     * [自动重置密码]
	     * @param request 
	     * @param currentUserInfo
	     * @return
	     */
	    @RequestMapping(method = RequestMethod.POST ,value ="/update", consumes = "application/json")
	    @ResponseBody
	    public BaseResponse updateUser(@RequestBody UserRequest request,@CurrentUser CurrentUserInfo currentUserInfo){
	    	 request.setId(request.getId());
	    	 //获取相关的盐
		     RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
			 String salt = randomNumberGenerator.nextBytes().toHex();
			 request.setSalt(salt);
	         request.setTerminalType(TerminalTypeEnum.TERMINAL_PC.getValue());
	            
			    //设置租户 id
			 request.setTenantId(currentUserInfo.getTenantId());
			 Integer loginType = request.getLoginType();
			    
			    //邮箱方式注册设置加密密码
			 if(LoginTypeEnum.SYSTEM_EMAIL_LOGIN.getType() == loginType){
			    	request.setUserPwd(this.getEncryptPassword(salt, request.getUserPwd(), null, request.getEmail()));
			    	
			    //手机方式注册设置加密密码
			 }else if(LoginTypeEnum.SYSTEM_CELLPHONE_LOGIN.getType() == loginType){
			    	request.setUserPwd(this.getEncryptPassword(salt, request.getUserPwd(), null, request.getCellPhoneNum()));
			 }
	    	 
	    	 //更新部分用户的信息
	    	 userService.updateApartUserInfo(request);
	    	 return BaseResponse.getSuccessResponse(new Date());	
	    }
	    
	    
	    /**
	     * 获取加密后的密码
	     * @param salt 盐
	     * @param password 明文密码
	     * @param telephone 手机号码
	     * @param email 邮箱
	     * @return
	     */
	    private  String getEncryptPassword(String salt,String password,String telephone,String email){
			 if(StringUtils.isNotEmpty(telephone)){
				 return new SimpleHash(EncryptionEnum.ALGORITHMNAME.getValue(),password,
			    			ByteSource.Util.bytes(telephone + salt),Integer.parseInt(EncryptionEnum.HASHITERATIONS.getValue())).toHex();
			 }else if(StringUtils.isNotEmpty(email)){
				 return new SimpleHash(EncryptionEnum.ALGORITHMNAME.getValue(),password,
			    			ByteSource.Util.bytes(email +salt),Integer.parseInt(EncryptionEnum.HASHITERATIONS.getValue())).toHex();
			 }
			 return null;
		 }
	    
	    
	    /**
		 * RSA加密[由服务器生成相关的私钥]
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(method = RequestMethod.GET,value="keyPair")
		public PublicKeyMap keyPair() throws Exception{
			PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
			return publicKeyMap;
		}
		
	    /**
	     * pc端后台进行登入
	     * @param request
	     * @return
	     * @throws Exception
	     */
		@SuppressWarnings("static-access")
		@RequestMapping(method = RequestMethod.POST, value = "login", consumes = "application/json")
		@ResponseBody
		public BaseResponse login(@RequestBody UserLoginRequest request)throws Exception {
			BaseResponse response = new BaseResponse();
			String pass = URLDecoder.decode(request.getPassword(), "UTF-8");
			//反转出前端的实际密码
			String password = RSAUtils.decryptStringByJs(pass); 
			if(StringUtils.isNotEmpty(password)){
				request.setPassword(password);
			}
			request.setTerminalType(TerminalTypeEnum.TERMINAL_PC.getValue());
			//进行Pc端登入
			String sessionId = securityService.login(request);
			
			if(StringUtils.isEmpty(sessionId)){
				return BaseResponse.getFailResponse("-200", "登入失败");
			}
			return response.getSuccessResponse(sessionId);
		} 
		
		/**
		 * pc 端后台登出
		 * @param currentUserInfo
		 * @return
		 */
		@RequestMapping(value="/logout",method=RequestMethod.GET,produces="application/json") 
		@RequiresAuthentication //表示当前用户登入之后才能够访问
	    public BaseResponse logout(@CurrentUser CurrentUserInfo currentUserInfo){
			securityService.logout();
	        return BaseResponse.getSuccessResponse(new Date());
	    } 
		
		
		/**
		 * 后台进行逻辑删除
		 * @param ids
		 * @return
		 */
		@RequestMapping(value="/delete",method=RequestMethod.GET,produces="application/json")
		@RequiresAuthentication//表示删除
		public BaseResponse delete(@RequestParam(value="ids") String ids){
			//删除用户
			userService.delete(IdSplitUtil.splitString2Long(ids));
			
			//删除用户角色
			userRoleService.deleteByUserId(IdSplitUtil.splitString2Long(ids));
			
			return BaseResponse.getSuccessResponse(new Date());
		}
		
		
		/**
		 *  修改密码
		 * @param modifyPwdDto
		 * @param currentUserInfo
		 * @return
		 */
		@RequestMapping(method = RequestMethod.POST,value="/modifyPwd",consumes = "application/json")
		public String modifyPwd(@RequestBody PasswordRequest request,@CurrentUser CurrentUserInfo currentUserInfo){
			UserResponse response = userService.queryUserById(currentUserInfo.getUserId());
			String currentLoginEmail = null;
			String currentLoginCellPhone = null;
			if (response != null) {
					//判断当前用户的登入方式
				    if(currentUserInfo.getLoginName().indexOf("@")>-1){
				    	currentLoginEmail = currentUserInfo.getLoginName();
				    	
				    }else{
				    	currentLoginCellPhone = currentUserInfo.getLoginName();
				    }
				    
				   //获取用户输入的老密码
					String inputOriginPwd = this.getEncryptPassword(response.getSalt(), request.getOldPwd(),currentLoginCellPhone , currentLoginEmail);
					
					//获取该用户本身密码
					String originPwd = response.getUserPwd();
					
					if (StringUtils.isNotEmpty(originPwd) && StringUtils.isNotEmpty(inputOriginPwd) && inputOriginPwd.equals(originPwd)) {
						//新密码或者确认密码没有填写
						if (StringUtils.isEmpty(request.getNewPwd()) || StringUtils.isEmpty(request.getConfirmPwd())) {
							return CheckPasswordEnum.PASSWORD_ENPTY_NEW.getCode();
							
						//新密码和确认密码不匹配	
						} else if (!request.getNewPwd().equals(request.getConfirmPwd())) {
							return CheckPasswordEnum.PASSWORD_CONFLICT.getCode();
							
						} else {
							//获取由新密码生成的加密密码
							String password = this.getEncryptPassword(response.getSalt(),request.getNewPwd(), currentLoginCellPhone , currentLoginEmail);
							response.setUserPwd(password);
							userService.updateUser(response);
							return CheckPasswordEnum.PASSWORD_SUCCESS.getCode();
						}
					} else {
						return CheckPasswordEnum.PASSWORD_WRONG_OLDPWD.getCode();// 表示输入的旧密码有问题
					}
			}
			return CheckPasswordEnum.PASSWORD_FALURE.getCode();
		}
}

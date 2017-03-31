package com.maoba.system.app.phone.controller;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maoba.common.enums.TerminalTypeEnum;
import com.maoba.facade.dto.requestdto.UserLoginRequest;
import com.maoba.facade.dto.responsedto.BaseResponse;
import com.maoba.facade.dto.responsedto.UserResponse;
import com.maoba.service.SecurityService;
import com.maoba.service.UserService;
/**
 * @author kitty daddy
 * http接口服务（主要给手机端进行使用）
 */
@RestController
@RequestMapping(value="/phone/user")
public class UserController_PH {
    @Autowired
    private UserService userService;
    
    @Autowired
    private SecurityService securityService;
    
    @RequestMapping(method = RequestMethod.GET,value="/queryUserById")
	@ResponseBody
    public UserResponse getUser(@RequestParam(value="userId") long userId){
    	UserResponse response =  userService.queryUserById(userId);
    	return response;
    }
    
    /**
	 * 手机端进行登入
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST,value="cellPhoneLogin",consumes = "application/json")
	@ResponseBody
	public BaseResponse telephoneLogin(@RequestBody UserLoginRequest request)throws Exception{
		 BaseResponse response = new BaseResponse();
		 request.setTerminalType(TerminalTypeEnum.TERMINAL_CELL_PHONE.getValue());
		 String sessionId = securityService.login(request);
		 if(StringUtils.isEmpty(sessionId)){
			 return response.getFailResponse("403", "手机端密码异常");
		 }
		 return response.getSuccessResponse(new Date());
	}
}

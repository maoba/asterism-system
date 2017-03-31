package com.maoba.system.app.handler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.maoba.facade.dto.responsedto.BaseResponse;
/**
 * @author kitty daddy
 * 全局异常情况处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private HttpServletResponse response;
	
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public BaseResponse defaultExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
		
		if(e!=null){
			if(e instanceof UnauthorizedException){
				 response.setStatus(403);
				 
				 return BaseResponse.getFailResponse("403", "没有访问权限");
			}
			
			if(e instanceof UnauthenticatedException){
				 response.setStatus(401);
				 
				 return BaseResponse.getFailResponse("401", "当前用户未登录");
			}
		}
		
		logger.error("请求出错", e);
		return BaseResponse.getFailResponse("500", "服务器繁忙，请稍后再试");
	}

}

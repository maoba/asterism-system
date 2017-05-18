package com.maoba.system.app.resolver;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.maoba.annotation.CurrentUser;
import com.maoba.annotation.CurrentUserInfo;
import com.maoba.util.CookieUtils;
import com.maoba.util.RedisUtil;
import com.maoba.util.SessionRKGenerateUtil;
/**
 * @author kitty daddy
 * 处理session缓存的拦截器
 * 将redis缓存的session数据存入容器CurrentUserInfo中
 */
@Service
public class HanderCurrentUserResolver implements HandlerMethodArgumentResolver{
    @Autowired
	private RedisUtil redisUtil;
    
//    @Autowired
//    private HttpServletRequest request;
    
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class);
	}
	
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		 CurrentUserInfo currentUserInfo = new CurrentUserInfo();
		 Class<?> klass = parameter.getParameterType();
		 if (klass.isAssignableFrom(CurrentUserInfo.class)) {  
			    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			    String sessionId = CookieUtils.getCookie(request, "sid");
			    currentUserInfo.setSessionId(sessionId);
				sessionId = SessionRKGenerateUtil.getByteKey(sessionId);
				Session session = (Session) redisUtil.getAttribute(sessionId);
				if(session!=null){
					String userName = (String) session.getAttribute("userName");
					if(StringUtils.isNotEmpty(userName)){
						currentUserInfo.setUserName(userName);
					}
					
					if(session.getAttribute("userId")!=null){
						long userId = (Long)session.getAttribute("userId");
						currentUserInfo.setUserId(userId);
					}
					
					if(session.getAttribute("tenantId")!=null){
						long tenantId = (Long)session.getAttribute("tenantId");
						currentUserInfo.setTenantId(tenantId);
					}
					
					if(session.getAttribute("tenantName")!=null){
						String tenantName = (String) session.getAttribute("tenantName");
						currentUserInfo.setTenantName(tenantName);
					}
					
					if(session.getAttribute("loginName")!=null){
						String loginName = (String) session.getAttribute("loginName");
						currentUserInfo.setLoginName(loginName);
					}
					currentUserInfo.setSessionId(sessionId);
				}
	     }  
		return currentUserInfo;
	}
}

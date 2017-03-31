package com.maoba.system.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.maoba.system.app.resolver.HanderCurrentUserResolver;

/**
 * @author kitty daddy
 *  配置注解拦截
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	 @Autowired
	 private HanderCurrentUserResolver handerCurrentUserResolver;
	 
     public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
         argumentResolvers.add(handerCurrentUserResolver);
     }
} 

package com.maoba.system.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author kitty daddy
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.maoba"})
public class SystemApplication {
	 public static void main(String[] args) {
	        // 程序启动入口
	        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
	        SpringApplication.run(SystemApplication.class,args);
	    }
}

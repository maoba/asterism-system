package com.maoba.common.enums;

/**
 * @author kitty daddy
 * 修改密码的时候返回码定义
 */
public enum CheckPasswordEnum {
	PASSWORD_ENPTY_NEW("checkInputPwd","新密码为空或者确认密码为空"),
    
    PASSWORD_CONFLICT("differentPwd","新密码和确认密码不一致"),
    
    PASSWORD_WRONG_OLDPWD("oldPwdNotEqual","旧密码错误"),
    
    PASSWORD_SUCCESS("success","成功"),
    
    PASSWORD_FALURE("failure","失败");
	
	
	private String code;
	
	
	private String description;

	
	private CheckPasswordEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
}

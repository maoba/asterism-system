package com.maoba.common.enums;
/**
 * @author kitty daddy
 * 登入方式枚举值
 */
public enum LoginTypeEnum {
    SYSTEM_CELLPHONE_LOGIN(0,"手机号码方式登入"),
    SYSTEM_EMAIL_LOGIN(1,"邮箱方式登入");
     
    /**
     * 登入方式類型
     */
    private Integer type;
     
    /**
     * 描述信息
     */
    private String description;

    
	private LoginTypeEnum(Integer type, String description) {
		this.type = type;
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
     
}

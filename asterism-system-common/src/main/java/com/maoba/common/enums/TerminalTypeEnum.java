package com.maoba.common.enums;
/**
 * @author kitty daddy
 * 终端类型枚举值
 */
public enum TerminalTypeEnum {
    TERMINAL_PC(0,"pc端"),
    TERMINAL_CELL_PHONE(1,"手机端");
     
     /**
      * 终端类型值
      */
    private Integer value;
     
     /**
      * 描述信息
      */
    private String description;

    
	private TerminalTypeEnum(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

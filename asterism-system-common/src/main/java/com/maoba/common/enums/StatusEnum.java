package com.maoba.common.enums;

/**
 * @author kitty daddy
 * 用户状态枚举值
 */
public enum StatusEnum {
     NORMAL(1,"正常状态"),
     ABNORMAL(0,"异常状态"),
     DELETE(-1,"删除状态"),
     LOCKED(2,"锁定状态");
     
     /**
      * 表示状态值
      */
     private Integer value;
     
     /**
      * 描述信息
      */
     private String description;
    
     
	 private StatusEnum(Integer value, String description) {
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

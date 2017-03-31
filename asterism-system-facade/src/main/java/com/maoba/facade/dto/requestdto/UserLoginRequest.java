package com.maoba.facade.dto.requestdto;
/**
 * @author kitty daddy
 * 用户登入请求
 */
public class UserLoginRequest {
	/**
	 * 手机号码
	 */
    private String cellPhoneNum;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 登入的终端类型
     * 0:pc端  1:手机端
     */
    private Integer terminalType;
    
    /**
     * 登入类型  
     * 0:手机登入 1: 邮箱登入
     */
    private Integer loginType;
    
	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public String getCellPhoneNum() {
		return cellPhoneNum;
	}

	public void setCellPhoneNum(String cellPhoneNum) {
		this.cellPhoneNum = cellPhoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

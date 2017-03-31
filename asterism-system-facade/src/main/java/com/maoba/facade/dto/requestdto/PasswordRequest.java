package com.maoba.facade.dto.requestdto;
/**
 * @author kitty daddy
 * 修改密码request
 */
public class PasswordRequest {
	/**
	 * 旧密码
	 */
	private String oldPwd;

	/**
	 * 新密码
	 */
	private String newPwd;

	/**
	 * 确认密码
	 */
	private String confirmPwd;
	
	/**
	 * 用户id
	 */
	private long  userId;

	
	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}

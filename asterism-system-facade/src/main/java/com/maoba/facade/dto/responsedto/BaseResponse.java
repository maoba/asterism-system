package com.maoba.facade.dto.responsedto;
import java.util.Date;
/**
 * @author kitty daddy
 *  基本返回dto
 */
public class BaseResponse {
	/**
	 * 返回状态
	 */
    private String status;
	
    /**
     * 返回时间
     */
	private Date cacheDate = new Date();
	
	/**
	 * 返回结果码
	 */
	private String resultCode;
	
	/**
	 * 返回信息
	 */
	private String msg;
	
	/**
	 * 返回数据
	 */
	private Object data;
	
	private Object filter;

	public static BaseResponse getSuccessResponse() {
		return getSuccessResponse(new Date(),null);
	}
	
	public static BaseResponse getSuccessResponse(Object data) {
		return getSuccessResponse(new Date(),data);
	}
	
	public static BaseResponse getSuccessResponse(Date cacheDate, Object data){
		BaseResponse response = new BaseResponse();
		response.setStatus("200");
		response.setResultCode("success");
		response.setMsg("成功");
		response.setData(data);
		return response;
	}
	
	public static BaseResponse getFailResponse(String status, String msg) {
		BaseResponse response = new BaseResponse();
		response.setStatus(status);
		response.setResultCode("fail");
		response.setMsg(msg);
		return response;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCacheDate() {
		return cacheDate;
	}

	public void setCacheDate(Date cacheDate) {
		this.cacheDate = cacheDate;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getFilter() {
		return filter;
	}

	public void setFilter(Object filter) {
		this.filter = filter;
	}
}

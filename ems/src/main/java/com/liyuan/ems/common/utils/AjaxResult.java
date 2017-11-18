package com.liyuan.ems.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * ajax请求返回结果
 * @author zy
 *
 */
public class AjaxResult {

	public static int CODE_SUCCESS = 0;
	public static int CODE_SESSION_EXPIRED = 401;
	public static int CODE_FAILURE = 500;

	private boolean success;	// 是否成功
	private int code;			// 状态码
	private String msg;			// 消息
	private Object data;		// 数据
	private String returnObject;

	public static AjaxResult success(Object data) {
		AjaxResult result = new AjaxResult();
		result.setCode(CODE_SUCCESS);
		result.setSuccess(true);
		result.setMsg("请求成功");
		result.setData(data);
		return result;
	}
	public static AjaxResult success(String msg) {
		AjaxResult result = new AjaxResult();
		result.setCode(CODE_SUCCESS);
		result.setSuccess(true);
		result.setMsg(StringUtils.isEmpty(msg) ? "请求成功" : msg);
		return result;
	}
	public static AjaxResult fail(String msg) {
		AjaxResult result = new AjaxResult();
		result.setCode(CODE_FAILURE);
		result.setSuccess(false);
		result.setMsg(StringUtils.isEmpty(msg) ? "请求失败" : msg);
		return result;
	}
	public static AjaxResult sessionExpired() {
		AjaxResult result = new AjaxResult();
		result.setCode(CODE_SESSION_EXPIRED);
		result.setSuccess(false);
		result.setMsg("登陆已过期, 请重新登陆");
		return result;
	}

	@Override
	public String toString() {
		return "AjaxResult{" +
				"success=" + success +
				", code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				", returnObject='" + returnObject + '\'' +
				'}';
	}



	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
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
	public String getReturnObject() {
		return returnObject;
	}
	public void setReturnObject(String returnObject) {
		this.returnObject = returnObject;
	}

}

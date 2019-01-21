package com.auto.applet.violation.common.support;

import java.io.Serializable;

import com.auto.applet.violation.common.constant.ReturnEnum;
import com.auto.applet.violation.common.util.JsonUtil;

/**
 * Rest服务 返回 结果类
 * 
 * @author (li_xiaodong)
 * @date 2016年1月19日 下午5:30:20
 * @param <T>
 */
public class Result<T> implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -7675268884737656024L;

	private String code;

	private String msg;

	private T data;

	// Extension拓展字段

	public Result() {
		// 实例化默认设置成功
		setSuccess();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @Title: setSuccess @Description: 设置成功 @param 设定文件 @return void 返回类型 @throws
	 */
	public void setSuccess() {
		this.setSuccess(ReturnEnum.SUCCESS.getReturnMsg());
	}

	/**
	 * 设置成功
	 */
	public void setSuccess(String successMsg) {
		this.setCode(ReturnEnum.SUCCESS.getReturnCode());
		this.setMsg(successMsg);
	}

	
	/**
	 * 设置失败
	 */
	public void setFail(String failNo, String failMsg) {
		this.setCode(failNo);
		this.setMsg(failMsg);
	}
	
	/**
	 * 设置返回 枚举
	 * @param returnEnum
	 */
	public void setReturn(ReturnEnum returnEnum) {
		this.setCode(returnEnum.getReturnCode());
		this.setMsg(returnEnum.getReturnMsg());
	}

	@Override
	public String toString() {
		return JsonUtil.parseToJson(this);
	}
}
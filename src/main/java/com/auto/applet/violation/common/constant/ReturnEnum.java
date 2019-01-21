package com.auto.applet.violation.common.constant;

/**
 * 返回码+返回MSG 枚举
 * 
 * @author li_xiaodong
 * @date 2018年7月25日
 */
public enum ReturnEnum {

	SUCCESS("200", "成功"),

	EMPTY_PARAM_USER_ID("4001", "用户Id不能为空"),

	EMPTY_PARAM_NUMBER("4002", "车牌号不能为空"),

	EMPTY_PARAM_CAR_ID("4003", "carId不能为空"),

	EMPTY_PARAM_BODY("4004", "请求对象不能为空"),

	UNBIND_CAR_NOT_NEED("4005", "请求信息有误或已解绑无需重复解绑"),

	LINCE_OCR_EMPTY_FAIL("4006", "行驶证识别失败,请检查图片清晰度"),

	EMPTY_PARAM_LINCE("4007", "行驶证识图片URL不能为空"),

	OUTER_LINCE_SERVER_ERROR("4008", "OCR识别（用的是车辆HTTP接口）服务异常"),

	OUTER_LINCE_SERVER_RETURN_NULL("4009", "OCR识别（用的是车辆HTTP接口）服务，返回结果为空"),

	OUTER_LINCE_SERVER_RETURN_JSON_FAIL("4010", "OCR识别返回信息转json异常"),

	LIMIT_ADD_CAR_NO("4020", "不可绑定车辆"),

	/**
	 * 已达到最大可绑定车辆数
	 */
	LIMIT_ADD_CAR("4021", "已达到最大可绑定车辆数"),
	
	/**
	 * 已达到单日可查询次数
	 */
	VIOLATION_LIMIT_QUERY_ONE_DAY("4030", "已达到单日可查询次数"),

	/**
	 * Invalid query
	 */
	VIOLATION_INVALID_QUERY("4031", "无效查询，请确认用户是否绑定该车辆");

	private String returnCode;

	private String returnMsg;

	private ReturnEnum(String returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}

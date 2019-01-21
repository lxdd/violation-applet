package com.auto.applet.violation.common.constant;

/**
 * 违章小程序-限制表
 * @author li_xiaodong
 * @date 2018年7月26日
 */
public enum LimitTypeEnum {

	/**
	 * 0 用户可查询次数限制
	 */
	LIMIT_USER_QUERY_NUMBER(0, "日查询次数限制"),

	/**
	 * 1 用户可绑定车辆数限制
	 */
	LIMIT_USER_ENABLE_BIND_CAR_NUMBER(1, "用户可绑定车辆数限制");

	private Integer limitTypeId;

	private String limitTypeName;

	private LimitTypeEnum(Integer limitTypeId, String limitTypeName) {
		this.limitTypeId = limitTypeId;
		this.limitTypeName = limitTypeName;
	}

	public Integer getLimitTypeId() {
		return limitTypeId;
	}

	public void setLimitTypeId(Integer limitTypeId) {
		this.limitTypeId = limitTypeId;
	}

	public String getLimitTypeName() {
		return limitTypeName;
	}

	public void setLimitTypeName(String limitTypeName) {
		this.limitTypeName = limitTypeName;
	}

}

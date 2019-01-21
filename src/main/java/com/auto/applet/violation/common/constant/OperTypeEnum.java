package com.auto.applet.violation.common.constant;

/**
 * 违章小程序-操作类型
 * 
 * @author li_xiaodong
 * @date 2018年7月26日
 */
public enum OperTypeEnum {

	/**
	 * 1 绑车
	 */
	BIND_CAR(1, "绑定车辆"),

	/**
	 * 2 解绑
	 */
	UN_BIND_CAR(2, "解绑车辆"),

	/**
	 * 3 列表
	 */
	CAR_LIST(3, "列表查询"),

	/**
	 * 4 查询违章
	 */
	QUERY_VIOLATION(4, "违章查询"),

	/**
	 * 5 license 行驶证识别
	 */
	LICENSE(5, "行驶证识别"),

	/**
	 * 6 是否可添加车辆-limitAddCar
	 */
	LIMIT_ADD_CAR(6, "是否可添加车辆"),

	/**
	 * 7 违章详情
	 */
	VIOLATION_DETAIL(7, "违章详情"),

	/**
	 * 绑车-信息有误
	 */
	BIND_CAR_FAIL(9, "绑定车辆-信息有误");

	private Integer operTypeId;

	private String operTypeName;

	private OperTypeEnum(Integer operTypeId, String operTypeName) {
		this.operTypeId = operTypeId;
		this.operTypeName = operTypeName;
	}

	public static String getValueOf(Integer getOperTypeId) {
		for (OperTypeEnum e : OperTypeEnum.values()) {
			if (e.getOperTypeId().equals(getOperTypeId)) {
				return e.getOperTypeName();
			}
		}

		return "";
	}

	public Integer getOperTypeId() {
		return operTypeId;
	}

	public void setOperTypeId(Integer operTypeId) {
		this.operTypeId = operTypeId;
	}

	public String getOperTypeName() {
		return operTypeName;
	}

	public void setOperTypeName(String operTypeName) {
		this.operTypeName = operTypeName;
	}

}

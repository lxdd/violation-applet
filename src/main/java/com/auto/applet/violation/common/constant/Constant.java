package com.auto.applet.violation.common.constant;

/**
 * @author li_xiaodong
 * @date 2018年7月26日
 */
public interface Constant {

	/**
	 * 0 对应数据表 isDeleted 字段，标识数据-有效
	 */
	Integer NOT_DELETED = 0;

	/**
	 * 1 对应数据表 isDeleted 字段，标识数据-无效
	 */
	Integer DELETED = 1;

	/**
	 * 0 对应limit 表 useNumber字段，，初始默认 为0
	 */
	Integer LIMIT_USE_NUMBER = 0;
	
	/**
	 * 可绑定车辆
	 */
	String LIMIT_ADD_CAR_YES = "可绑定车辆";
	
	/**
	 * 可绑定车辆
	 */
	String LINCESE_SUCESS = "行驶证识别成功 ";

}

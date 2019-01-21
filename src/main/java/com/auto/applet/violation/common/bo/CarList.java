package com.auto.applet.violation.common.bo;

import java.io.Serializable;

/**
 * 违章小程序-添加车辆VO
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
public class CarList extends ViolationBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String vin;
	private String engine;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	@Override
	public String toString() {
		return "CarList [vin=" + vin + ", engine=" + engine + "]";
	}

}

package com.auto.applet.violation.common.vo;

/**
 * 违章小程序-添加车辆VO
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
public class BindCarVO {

	private String cSideUserId;
	private String number;
	private String vin;
	private String engine;
	private Integer openSmsRemind;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

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

	public String getcSideUserId() {
		return cSideUserId;
	}

	public void setcSideUserId(String cSideUserId) {
		this.cSideUserId = cSideUserId;
	}

	public Integer getOpenSmsRemind() {
		return openSmsRemind;
	}

	public void setOpenSmsRemind(Integer openSmsRemind) {
		this.openSmsRemind = openSmsRemind;
	}

	@Override
	public String toString() {
		return "AddCarVO [cSideUserId=" + cSideUserId + ", number=" + number + ", vin=" + vin + ", engine=" + engine
				+ "]";
	}

}

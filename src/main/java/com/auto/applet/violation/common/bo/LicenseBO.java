package com.auto.applet.violation.common.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 行驶证识别
 * 
 * @author li_xiaodong
 * @date 2018年7月30日
 */
public class LicenseBO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户Id
	 */
	private String cSideUserId;

	/**
	 * 车辆识别代号
	 */
	private String vin;

	/**
	 * 车牌号
	 */
	private String number;

	/**
	 * 发动机号
	 */
	private String engine;

	/**
	 * 所有人
	 */
	private String owner;

	/**
	 * 行驶证注册时间
	 */
	private Date registerTime;

	public String getcSideUserId() {
		return cSideUserId;
	}

	public void setcSideUserId(String cSideUserId) {
		this.cSideUserId = cSideUserId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	@Override
	public String toString() {
		return "LicenseBO [cSideUserId=" + cSideUserId + ", vin=" + vin + ", number=" + number + ", engine=" + engine
				+ ", owner=" + owner + ", registerTime=" + registerTime + "]";
	}

}

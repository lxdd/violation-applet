package com.auto.applet.violation.common.vo;

/**
 * 违章小程序-解绑车辆VO
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
public class UnBindCarVO {

	private String cSideUserId;
	private String carId;

	public UnBindCarVO(String cSideUserId, String carId) {
		super();
		this.cSideUserId = cSideUserId;
		this.carId = carId;
	}

	public String getcSideUserId() {
		return cSideUserId;
	}

	public void setcSideUserId(String cSideUserId) {
		this.cSideUserId = cSideUserId;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	@Override
	public String toString() {
		return "UnBindCarVO [cSideUserId=" + cSideUserId + ", carId=" + carId + "]";
	}

}

package com.auto.applet.violation.common.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 违章小程序-添加车辆VO
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
public class ListCarBO extends ViolationBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cSideUserId;
	private List<CarList> CarListBO;

	public String getcSideUserId() {
		return cSideUserId;
	}

	public void setcSideUserId(String cSideUserId) {
		this.cSideUserId = cSideUserId;
	}

	public List<CarList> getCarListBO() {
		return CarListBO;
	}

	public void setCarListBO(List<CarList> carListBO) {
		CarListBO = carListBO;
	}

	@Override
	public String toString() {
		return "ListCarBO [cSideUserId=" + cSideUserId + ", CarListBO=" + CarListBO + "]";
	}

}

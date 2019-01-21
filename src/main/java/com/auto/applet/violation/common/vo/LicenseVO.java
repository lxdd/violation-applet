package com.auto.applet.violation.common.vo;

/**
 * 违章小程序-添加车辆VO
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
public class LicenseVO {

	private String cSideUserId;
	private String lince;

	public String getcSideUserId() {
		return cSideUserId;
	}

	public void setcSideUserId(String cSideUserId) {
		this.cSideUserId = cSideUserId;
	}

	public String getLince() {
		return lince;
	}

	public void setLince(String lince) {
		this.lince = lince;
	}

	@Override
	public String toString() {
		return "DrivingLicenseVO [cSideUserId=" + cSideUserId + ", lince=" + lince + "]";
	}

}

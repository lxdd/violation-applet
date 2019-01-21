package com.auto.applet.violation.common.dto;

import java.io.Serializable;

/**
 * BO（Business Object）：业务对象
 * 
 * ocrLicence(String url)：通过图片url，识别行驶证，(返回车牌号、注册日期、vin码、发动机号、所有者)
 * 
 * 行驶证识别（车牌号 注册日期 vin码 所有者（效果不理想））
 * 
 * @author li_xiaodong
 *
 */
public class DrivingLicenseResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errno;
	private String errmsg;

	private DrivingLicenseDTO data;

	public String getErrno() {
		return errno;
	}

	public void setErrno(String errno) {
		this.errno = errno;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public DrivingLicenseDTO getData() {
		return data;
	}

	public void setData(DrivingLicenseDTO data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DrivingLicenseResponse [errno=" + errno + ", errmsg=" + errmsg + ", data=" + data + "]";
	}

}

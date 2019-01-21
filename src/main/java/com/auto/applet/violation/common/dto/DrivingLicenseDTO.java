package com.auto.applet.violation.common.dto;

import java.io.Serializable;
import java.util.Date;

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
public class DrivingLicenseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private String engineNumber;

	/**
	 * 所有人
	 */
	private String owner;

	/**
	 * 行驶证注册时间
	 */
	private Date registerTime;

	/**
	 * OCR识别信息的Id
	 */
	private Long ocrId;

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

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
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

	public Long getOcrId() {
		return ocrId;
	}

	public void setOcrId(Long ocrId) {
		this.ocrId = ocrId;
	}

	@Override
	public String toString() {
		return "CarBaseOCRBO [vin=" + vin + ", number=" + number + ", engineNumber=" + engineNumber + ", owner=" + owner
				+ ", registerTime=" + registerTime + ", ocrId=" + ocrId + "]";
	}

}

package com.auto.applet.violation.common.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViolationBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String carId;

	private String cSideUserId;

	private String number;

	/**
	 * 违章最近一次查询违章的事假
	 */
	private Date lastQueryTime;

	/**
	 * 用户最近一次查询违章的时间（一键查询+列表）
	 */
	private Date gmtLatelyQuery;

	private Integer violationCount;

	private Integer totalFine;

	private Integer totalDegree;

	private List<ViolationDetailBO> violationDetails = new ArrayList<>();

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getcSideUserId() {
		return cSideUserId;
	}

	public void setcSideUserId(String cSideUserId) {
		this.cSideUserId = cSideUserId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getLastQueryTime() {
		return lastQueryTime;
	}

	public void setLastQueryTime(Date lastQueryTime) {
		this.lastQueryTime = lastQueryTime;
	}

	public Date getGmtLatelyQuery() {
		return gmtLatelyQuery;
	}

	public void setGmtLatelyQuery(Date gmtLatelyQuery) {
		this.gmtLatelyQuery = gmtLatelyQuery;
	}

	public Integer getViolationCount() {
		return violationCount;
	}

	public void setViolationCount(Integer violationCount) {
		this.violationCount = violationCount;
	}

	public Integer getTotalFine() {
		return totalFine;
	}

	public void setTotalFine(Integer totalFine) {
		this.totalFine = totalFine;
	}

	public Integer getTotalDegree() {
		return totalDegree;
	}

	public void setTotalDegree(Integer totalDegree) {
		this.totalDegree = totalDegree;
	}

	public List<ViolationDetailBO> getViolationDetails() {
		return violationDetails;
	}

	public void setViolationDetails(List<ViolationDetailBO> violationDetails) {
		this.violationDetails = violationDetails;
	}

	@Override
	public String toString() {
		return "ViolationBO [carId=" + carId + ", cSideUserId=" + cSideUserId + ", number=" + number
				+ ", lastQueryTime=" + lastQueryTime + ", gmtLatelyQuery=" + gmtLatelyQuery + ", violationCount="
				+ violationCount + ", totalFine=" + totalFine + ", totalDegree=" + totalDegree + ", violationDetails="
				+ violationDetails + "]";
	}

}

package com.auto.applet.violation.common.dto;

import java.io.Serializable;
import java.util.Date;

public class ViolationRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long violationId;
	private String violationCode;
	private String violationLocation;
	private String violationCity;
	private String violationReason;
	private Integer fine;
	private Integer degree;
	private Date violationOccurTime;

	private Date violationQueryTime;
	private Date carLastUpdateTime;
	private Date violationLastUpdateTime;
	private Date violationFirstQueryTime;

	private Integer violationUpdateType;
	private Integer violationAbnormalReason;

	private ViolationExtendInfo violationExtendInfo;

	public ViolationExtendInfo getViolationExtendInfo() {
		return violationExtendInfo;
	}

	public void setViolationExtendInfo(ViolationExtendInfo violationExtendInfo) {
		this.violationExtendInfo = violationExtendInfo;
	}

	public Integer getViolationUpdateType() {
		return violationUpdateType;
	}

	public void setViolationUpdateType(Integer violationUpdateType) {
		this.violationUpdateType = violationUpdateType;
	}

	public Integer getViolationAbnormalReason() {
		return violationAbnormalReason;
	}

	public void setViolationAbnormalReason(Integer violationAbnormalReason) {
		this.violationAbnormalReason = violationAbnormalReason;
	}

	public Date getViolationOccurTime() {
		return violationOccurTime;
	}

	public void setViolationOccurTime(Date violationOccurTime) {
		this.violationOccurTime = violationOccurTime;
	}

	public Date getViolationQueryTime() {
		return violationQueryTime;
	}

	public void setViolationQueryTime(Date violationQueryTime) {
		this.violationQueryTime = violationQueryTime;
	}

	public Date getCarLastUpdateTime() {
		return carLastUpdateTime;
	}

	public void setCarLastUpdateTime(Date carLastUpdateTime) {
		this.carLastUpdateTime = carLastUpdateTime;
	}

	public Date getViolationLastUpdateTime() {
		return violationLastUpdateTime;
	}

	public void setViolationLastUpdateTime(Date violationLastUpdateTime) {
		this.violationLastUpdateTime = violationLastUpdateTime;
	}

	public Date getViolationFirstQueryTime() {
		return violationFirstQueryTime;
	}

	public void setViolationFirstQueryTime(Date violationFirstQueryTime) {
		this.violationFirstQueryTime = violationFirstQueryTime;
	}

	public Long getViolationId() {
		return violationId;
	}

	public void setViolationId(Long violationId) {
		this.violationId = violationId;
	}

	public String getViolationCode() {
		return violationCode;
	}

	public void setViolationCode(String violationCode) {
		this.violationCode = violationCode;
	}

	public String getViolationLocation() {
		return violationLocation;
	}

	public void setViolationLocation(String violationLocation) {
		this.violationLocation = violationLocation;
	}

	public String getViolationCity() {
		return violationCity;
	}

	public void setViolationCity(String violationCity) {
		this.violationCity = violationCity;
	}

	public String getViolationReason() {
		return violationReason;
	}

	public void setViolationReason(String violationReason) {
		this.violationReason = violationReason;
	}

	public Integer getFine() {
		return fine;
	}

	public void setFine(Integer fine) {
		this.fine = fine;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	@Override
	public String toString() {
		return "ViolationRecord{" + "violationId=" + violationId + ", violationCode='" + violationCode + '\''
				+ ", violationLocation='" + violationLocation + '\'' + ", violationCity='" + violationCity + '\''
				+ ", violationReason='" + violationReason + '\'' + ", fine=" + fine + ", degree=" + degree
				+ ", violationOccurTime=" + violationOccurTime + ", violationQueryTime=" + violationQueryTime
				+ ", carLastUpdateTime=" + carLastUpdateTime + ", violationLastUpdateTime="
				+ violationLastUpdateTime + ", violationFirstQueryTime=" + violationFirstQueryTime
				+ ", violationUpdateType=" + violationUpdateType + ", violationAbnormalReason="
				+ violationAbnormalReason + ", violationExtendInfo=" + violationExtendInfo + '}';
	}
}

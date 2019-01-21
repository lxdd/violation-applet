package com.auto.applet.violation.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 违章信息
 * @author li_xiaodong
 * @date 2018年7月24日
 */
public class ViolationResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private String carNo;
	private String carEngine;
	private String carVin;
	private Integer queryType;
	private Integer dataSupplier;
	private Integer dataSupplierErrCode;
	private Integer autoPlatformErrCode;
	private String autoPlatformErrMsg;
	private Date queryTime;
	private Boolean isNeedFee;
	private String dataSource = "supplier";// 数据获取来源

	private List<ViolationRecord> violationRecords = new ArrayList<>();

	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}

	public String getAutoPlatformErrMsg() {
		return autoPlatformErrMsg;
	}

	public void setAutoPlatformErrMsg(String autoPlatformErrMsg) {
		this.autoPlatformErrMsg = autoPlatformErrMsg;
	}

	public Integer getDataSupplier() {
		return dataSupplier;
	}

	public void setDataSupplier(Integer dataSupplier) {
		this.dataSupplier = dataSupplier;
	}

	public Integer getDataSupplierErrCode() {
		return dataSupplierErrCode;
	}

	public void setDataSupplierErrCode(Integer dataSupplierErrCode) {
		this.dataSupplierErrCode = dataSupplierErrCode;
	}

	public Integer getAutoPlatformErrCode() {
		return autoPlatformErrCode;
	}

	public void setAutoPlatformErrCode(Integer autoPlatformErrCode) {
		this.autoPlatformErrCode = autoPlatformErrCode;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public List<ViolationRecord> getViolationRecords() {
		return violationRecords;
	}

	public void setViolationRecords(List<ViolationRecord> violationRecords) {
		this.violationRecords = violationRecords;
	}

	public String getCarEngine() {
		return carEngine;
	}

	public void setCarEngine(String carEngine) {
		this.carEngine = carEngine;
	}

	public String getCarVin() {
		return carVin;
	}

	public void setCarVin(String carVin) {
		this.carVin = carVin;
	}

	public Boolean getIsNeedFee() {
		return isNeedFee;
	}

	public void setIsNeedFee(Boolean isNeedFee) {
		this.isNeedFee = isNeedFee;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		return "ViolationResult{" + "carNo='" + carNo + '\'' + ", carEngine='" + carEngine + '\'' + ", carVin='"
				+ carVin + '\'' + ", queryType=" + queryType + ", dataSupplier=" + dataSupplier
				+ ", dataSupplierErrCode=" + dataSupplierErrCode + ", autoPlatformErrCode=" + autoPlatformErrCode
				+ ", autoPlatformErrMsg='" + autoPlatformErrMsg + '\'' + ", queryTime=" + queryTime + ", isNeedFee="
				+ isNeedFee + ", dataSource='" + dataSource + '\'' + ", violationRecords=" + violationRecords + '}';
	}

}
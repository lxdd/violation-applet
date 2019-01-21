package com.auto.applet.violation.common.dto;

import java.io.Serializable;

class ViolationExtendInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String department;
	private String archive;
	private String telephone;
	private String excutelocation;
	private String excutedepartment;
	private String category;
	private String latefine;
	private String punishmentaccording;
	private String illegalentry;
	private String locationid;
	private String dataSourceID;
	private String recordType;
	private String poundage;
	private String canProcess;
	private String uniqueCode;
	private Long secondaryUniqueCode;
	private Integer degreePoundage;
	private String canprocessMsg;
	private String proxyRule;
	private String other;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getArchive() {
		return archive;
	}

	public void setArchive(String archive) {
		this.archive = archive;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getExcutelocation() {
		return excutelocation;
	}

	public void setExcutelocation(String excutelocation) {
		this.excutelocation = excutelocation;
	}

	public String getExcutedepartment() {
		return excutedepartment;
	}

	public void setExcutedepartment(String excutedepartment) {
		this.excutedepartment = excutedepartment;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLatefine() {
		return latefine;
	}

	public void setLatefine(String latefine) {
		this.latefine = latefine;
	}

	public String getPunishmentaccording() {
		return punishmentaccording;
	}

	public void setPunishmentaccording(String punishmentaccording) {
		this.punishmentaccording = punishmentaccording;
	}

	public String getIllegalentry() {
		return illegalentry;
	}

	public void setIllegalentry(String illegalentry) {
		this.illegalentry = illegalentry;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getDataSourceID() {
		return dataSourceID;
	}

	public void setDataSourceID(String dataSourceID) {
		this.dataSourceID = dataSourceID;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getPoundage() {
		return poundage;
	}

	public void setPoundage(String poundage) {
		this.poundage = poundage;
	}

	public String getCanProcess() {
		return canProcess;
	}

	public void setCanProcess(String canProcess) {
		this.canProcess = canProcess;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public Long getSecondaryUniqueCode() {
		return secondaryUniqueCode;
	}

	public void setSecondaryUniqueCode(Long secondaryUniqueCode) {
		this.secondaryUniqueCode = secondaryUniqueCode;
	}

	public Integer getDegreePoundage() {
		return degreePoundage;
	}

	public void setDegreePoundage(Integer degreePoundage) {
		this.degreePoundage = degreePoundage;
	}

	public String getCanprocessMsg() {
		return canprocessMsg;
	}

	public void setCanprocessMsg(String canprocessMsg) {
		this.canprocessMsg = canprocessMsg;
	}

	public String getProxyRule() {
		return proxyRule;
	}

	public void setProxyRule(String proxyRule) {
		this.proxyRule = proxyRule;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return "ViolationExtendInfo{" + "department='" + department + '\'' + ", archive='" + archive + '\''
				+ ", telephone='" + telephone + '\'' + ", excutelocation='" + excutelocation + '\''
				+ ", excutedepartment='" + excutedepartment + '\'' + ", category='" + category + '\''
				+ ", latefine='" + latefine + '\'' + ", punishmentaccording='" + punishmentaccording + '\''
				+ ", illegalentry='" + illegalentry + '\'' + ", locationid='" + locationid + '\''
				+ ", dataSourceID='" + dataSourceID + '\'' + ", recordType='" + recordType + '\'' + ", poundage='"
				+ poundage + '\'' + ", canProcess='" + canProcess + '\'' + ", uniqueCode='" + uniqueCode + '\''
				+ ", secondaryUniqueCode=" + secondaryUniqueCode + ", degreePoundage=" + degreePoundage
				+ ", canprocessMsg='" + canprocessMsg + '\'' + ", proxyRule='" + proxyRule + '\'' + ", other='"
				+ other + '\'' + '}';
	}
}

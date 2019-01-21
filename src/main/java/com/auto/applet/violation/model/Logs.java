package com.auto.applet.violation.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.auto.applet.violation.common.constant.OperTypeEnum;

/**
 * 违章小程序-日志表
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
@Document(collection = "violation-applet-log")
public class Logs {

	/**
	 * 注解属性id为ID--主键，不可重复，自带索引，可以在定义的列名上标注，需要自己生成并维护不重复的约束
	 */
	@Id
	private String id;

	/**
	 * C端用户标识
	 */
	/**
	 * 注解属性cSideUserId为索引，并且不能重复
	 */
	private String cSideUserId;

	/**
	 * 车辆Id
	 */
	private String carId;

	/**
	 * 操作类型：1、绑定车辆；2、查询违章；3、解绑车辆；
	 */
	private Integer operType;

	/**
	 * 操作类型名：1、绑定车辆；2、查询违章；3、解绑车辆；
	 */
	private String operTypeName;

	/**
	 * 操作结果： 200 成功； 其它 失败
	 */
	private String operResultCode;

	/**
	 * 操作信息：
	 */
	private String operContent;

	@NotNull
	private Date gmtCreate = new Date();

	@NotNull
	private Integer isDeleted = 0;

	public Logs(String cSideUserId, String carId, Integer operType, String operResultCode, String operContent) {
		super();
		this.cSideUserId = cSideUserId;
		this.carId = carId;
		this.operType = operType;
		this.operResultCode = operResultCode;
		this.operContent = operContent;
		this.operTypeName = OperTypeEnum.getValueOf(operType);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public String getOperTypeName() {
		return operTypeName;
	}

	public void setOperTypeName(String operTypeName) {
		this.operTypeName = operTypeName;
	}

	public String getOperResultCode() {
		return operResultCode;
	}

	public void setOperResultCode(String operResultCode) {
		this.operResultCode = operResultCode;
	}

	public String getOperContent() {
		return operContent;
	}

	public void setOperContent(String operContent) {
		this.operContent = operContent;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Logs [id=" + id + ", cSideUserId=" + cSideUserId + ", carId=" + carId + ", operType=" + operType
				+ ", operTypeName=" + operTypeName + ", operResultCode=" + operResultCode + ", operContent=" + operContent
				+ ", gmtCreate=" + gmtCreate + ", isDeleted=" + isDeleted + "]";
	}

}

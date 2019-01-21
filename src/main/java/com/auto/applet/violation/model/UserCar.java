package com.auto.applet.violation.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 违章小程序-用户车辆关系表
 * cSideUserId和carId将作为复合索引，数字参数指定索引的方向，1为正序，-1为倒序。方向对单键索引和随机存不要紧，但如果你要执行分组和排序操作的时候，它就非常重要了。
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
@Document(collection = "violation-applet-userCarMapping")
@CompoundIndexes({ @CompoundIndex(name = "userId_carId_idx", def = "{'cSideUserId': 1, 'carId': 1}") })
public class UserCar {

	/**
	 * 注解属性id为ID--主键，不可重复，自带索引，可以在定义的列名上标注，需要自己生成并维护不重复的约束
	 */
	@Id
	private String id;

	/**
	 * C端用户标识
	 */
	private String cSideUserId;

	/**
	 * 车辆主键Id
	 */
	private String carId;

	/**
	 * 预约违章短信提醒（0默认不开通、1开通）
	 */
	@NotNull
	private Integer openSmsRemind = 0;

	/**
	 * 用户最近一次查询违章的时间（一键查询+列表）
	 */
	private Date gmtLatelyQuery;

	@NotNull
	private Date gmtModified = new Date();

	@NotNull
	private Date gmtCreate = new Date();

	@NotNull
	private Integer isDeleted = 0;

	public UserCar() {

	}

	public UserCar(String cSideUserId, String carId) {
		this.cSideUserId = cSideUserId;
		this.carId = carId;
		this.openSmsRemind = 0;
	}

	public UserCar(String cSideUserId, String carId, Integer openSmsRemind) {
		this.cSideUserId = cSideUserId;
		this.carId = carId;
		this.openSmsRemind = openSmsRemind;
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

	public Integer getOpenSmsRemind() {
		return openSmsRemind;
	}

	public void setOpenSmsRemind(Integer openSmsRemind) {
		this.openSmsRemind = openSmsRemind;
	}

	public Date getGmtLatelyQuery() {
		return gmtLatelyQuery;
	}

	public void setGmtLatelyQuery(Date gmtLatelyQuery) {
		this.gmtLatelyQuery = gmtLatelyQuery;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
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
		return "UserCar [id=" + id + ", cSideUserId=" + cSideUserId + ", carId=" + carId + ", openSmsRemind="
				+ openSmsRemind + ", gmtLatelyQuery=" + gmtLatelyQuery + ", gmtModified=" + gmtModified + ", gmtCreate="
				+ gmtCreate + ", isDeleted=" + isDeleted + "]";
	}

}

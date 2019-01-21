package com.auto.applet.violation.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 违章小程序-限制表
 * cSideUserId和carId将作为复合索引，数字参数指定索引的方向，1为正序，-1为倒序。方向对单键索引和随机存不要紧，但如果你要执行分组和排序操作的时候，它就非常重要了。
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
@Document(collection = "violation-applet-limit")
public class Limit {

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
	 * 限制类型（0 用户单日可查询次数限制、1 用户可绑定车辆数限制）
	 */
	private Integer limitType;

	/**
	 * 限制额度
	 */
	private Integer limitNum;

	/**
	 * 使用额度
	 */
	private Integer useNum;

	@NotNull
	private Date gmtModified = new Date();

	@NotNull
	private Date gmtCreate = new Date();

	@NotNull
	private Integer isDeleted = 0;

	public Limit(String cSideUserId, Integer limitType, Integer limitNum, Integer useNum) {
		super();
		this.cSideUserId = cSideUserId;
		this.limitType = limitType;
		this.limitNum = limitNum;
		this.useNum = useNum;
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

	public Integer getLimitType() {
		return limitType;
	}

	public void setLimitType(Integer limitType) {
		this.limitType = limitType;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
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
		return "Limit [id=" + id + ", cSideUserId=" + cSideUserId + ", limitType=" + limitType + ", limitNum="
				+ limitNum + ", useNum=" + useNum + ", gmtModified=" + gmtModified + ", gmtCreate=" + gmtCreate
				+ ", isDeleted=" + isDeleted + "]";
	}

}

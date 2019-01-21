package com.auto.applet.violation.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 违章小程序-用户表
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
@Document(collection = "violation-applet-user") // MongoDB是文档型的NoSQL数据库，因此，我们使用@Document注解User类
public class User {

	/**
	 * 注解属性id为ID
	 */
	@Id
	private String id;

	/**
	 * C端用户标识
	 */
	/**
	 * 注解属性cSideUserId为索引，并且不能重复
	 */
	@Indexed(unique = true)
	private String cSideUserId;

	@NotNull
	private Date gmtModified = new Date();

	@NotNull
	private Date gmtCreate = new Date();

	@NotNull
	private Integer isDeleted = 0;

	public User(String cSideUserId) {
		super();
		this.cSideUserId = cSideUserId;
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
		return "User [id=" + id + ", cSideUserId=" + cSideUserId + ", gmtModified=" + gmtModified + ", gmtCreate="
				+ gmtCreate + ", isDeleted=" + isDeleted + "]";
	}

}

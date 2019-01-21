package com.auto.applet.violation.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 违章小程序-车辆表
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */

@Document(collection = "violation-applet-car") // MongoDB是文档型的NoSQL数据库，因此，我们使用@Document注解User类
public class Car {

	/**
	 * 注解属性id为ID
	 */
	@Id
	private String id;

	/**
	 * 注解属性number为索引，并且不能重复
	 */
	@Indexed(unique = true)
	private String number;

	private String vin;
	private String engine;

	private String violation;

	@NotNull
	private Date gmtModified = new Date();

	@NotNull
	private Date gmtCreate = new Date();

	@NotNull
	private Integer isDeleted = 0;

	public Car(String number, String vin, String engine) {
		super();
		this.number = number;
		this.vin = vin;
		this.engine = engine;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getViolation() {
		return violation;
	}

	public void setViolation(String violation) {
		this.violation = violation;
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
		return "Car [id=" + id + ", number=" + number + ", vin=" + vin + ", engine=" + engine + ", violation="
				+ violation + ", gmtModified=" + gmtModified + ", gmtCreate=" + gmtCreate + ", isDeleted=" + isDeleted
				+ "]";
	}

}

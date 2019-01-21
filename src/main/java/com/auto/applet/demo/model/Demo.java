package com.auto.applet.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 入lombok库，它能够通过注解的方式为我们添加必要的Getter/Setter/hashCode()/equals()/toString()/构造方法等
 * MongoDB会自动创建collection，默认为类名首字母小写，也就是user
 * @author li_xiaodong
 * @date 2018年6月1日
 */
@Data // 生成无参构造方法/getter/setter/hashCode/equals/toString
@AllArgsConstructor // 生成所有参数构造方法
@NoArgsConstructor // @AllArgsConstructor会导致@Data不生成无参构造方法，需要手动添加@NoArgsConstructor，如果没有无参构造方法，可能会导致比如com.fasterxml.jackson在序列化处理时报错
@Document // MongoDB是文档型的NoSQL数据库，因此，我们使用@Document注解User类
public class Demo {

	/**
	 * 注解属性id为ID
	 */
	@Id
	private String id;

	/**
	 * 注解属性username为索引，并且不能重复
	 */
	@Indexed(unique = true)
	private String username;

	private String name;

	private String phone;

	private Date birthday;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
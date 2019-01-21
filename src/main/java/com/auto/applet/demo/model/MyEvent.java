package com.auto.applet.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 生成无参构造方法/getter/setter/hashCode/equals/toString
@AllArgsConstructor // 生成所有参数构造方法
@NoArgsConstructor // @AllArgsConstructor会导致@Data不生成无参构造方法，需要手动添加@NoArgsConstructor，如果没有无参构造方法，可能会导致比如com.fasterxml.jackson在序列化处理时报错
@Document(collection = "event") // 指定collection名为event；
public class MyEvent {
	@Id
	private Long id; // 这次我们使用表示时间的long型数据作为ID
	private String message;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MyEvent [id=" + id + ", message=" + message + "]";
	}

}
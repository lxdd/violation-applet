package com.auto.applet.violation.common.dto;

import java.io.Serializable;

public class ViolationResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String errno;
	private String errmsg;
	private ViolationResult data;

	public String getErrno() {
		return errno;
	}

	public void setErrno(String errno) {
		this.errno = errno;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public ViolationResult getData() {
		return data;
	}

	public void setData(ViolationResult data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ViolationJson{" +
				"errno='" + errno + '\'' +
				", errmsg='" + errmsg + '\'' +
				", data=" + data +
				'}';
	}
}
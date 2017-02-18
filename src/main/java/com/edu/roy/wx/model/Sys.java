package com.edu.roy.wx.model;

import com.edu.roy.wx.comm.Entity;

public class Sys extends Entity {
	
	private String sysKey;
	private String sysValue;
	private String sysComment;
	public String getSysComment() {
		return sysComment;
	}
	public void setSysComment(String sysComment) {
		this.sysComment = sysComment;
	}
	public String getSysKey() {
		return sysKey;
	}
	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}
	public String getSysValue() {
		return sysValue;
	}
	public void setSysValue(String sysValue) {
		this.sysValue = sysValue;
	}
	
}

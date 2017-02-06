package com.edu.roy.wx.comm;

import java.util.Date;

import com.edu.roy.wx.tools.JsonTools;

public class Entity {
	
	private long id;
	private Date gmtCreated;
	private Date gmtModified;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	@Override
	public String toString() {
		return JsonTools.toJson(this);
	}

}

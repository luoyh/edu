package com.edu.roy.wx.vo;

import com.edu.roy.wx.model.DrillResult;

/**
 * 
 * @author luoyh
 * @date Feb 10, 2017
 */
public class HomeResultVO extends DrillResult {
	private int total;
	private int wrongs;
	private String suiteTitle;
	private String subjectName;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getWrongs() {
		return wrongs;
	}
	public void setWrongs(int wrongs) {
		this.wrongs = wrongs;
	}
	public String getSuiteTitle() {
		return suiteTitle;
	}
	public void setSuiteTitle(String suiteTitle) {
		this.suiteTitle = suiteTitle;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
}

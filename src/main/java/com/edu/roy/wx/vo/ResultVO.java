package com.edu.roy.wx.vo;

import com.edu.roy.wx.model.DrillResult;

public class ResultVO extends DrillResult {

	private String memberName;
	private String suiteTitle;
	private String subjectName;
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
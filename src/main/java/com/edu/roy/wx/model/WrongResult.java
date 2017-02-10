package com.edu.roy.wx.model;

import com.edu.roy.wx.comm.Entity;

/**
 * 
 * @author luoyh
 * @date Feb 9, 2017
 */
public class WrongResult extends Entity {
	private long memberId;
	private long subjectId;
	private long suiteId;
	private long questionId;
	private String answers;
	private int cnd; // default 0, 0:normal,1:solved
	
	public int getCnd() {
		return cnd;
	}
	public void setCnd(int cnd) {
		this.cnd = cnd;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public long getSuiteId() {
		return suiteId;
	}
	public void setSuiteId(long suiteId) {
		this.suiteId = suiteId;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	
	
}

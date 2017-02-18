package com.edu.roy.wx.model;

import com.edu.roy.wx.comm.Entity;

/**
 * 
 * @author luoyh
 * @date Feb 9, 2017
 */
public class DrillRecord extends Entity {
	
	public static enum Result {
		VOID(0),
		INCERTITUDE(1),
		RIGHT(2),
		WRONG(3),
		PART(4)
		;
		public final int code;
		private Result(int code) {
			this.code = code;
		}
	}

	private long memberId;
	private long resultId;
	private int type; // 0:order,1:suite,2:wrong
	private int result; // 0:void,1:incertitude,2:right,3:wrong,4:part
	private long subjectId; 
	private long suiteId;
	private long questionId;
	private String answers;
	private int score;
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public long getResultId() {
		return resultId;
	}
	public void setResultId(long resultId) {
		this.resultId = resultId;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
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



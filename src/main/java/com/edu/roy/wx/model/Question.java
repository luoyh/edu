package com.edu.roy.wx.model;

import com.edu.roy.wx.comm.Entity;

public class Question extends Entity {
	
	public static enum AdjunctType {
		IMAGE(0),
		AUDIO(1),
		VIDEO(2)
		;
		public final int code;
		private AdjunctType(int code) {
			this.code = code;
		}
	}

	private int sort;
	private String title;
	private int type;
	private long suiteId;
	private long subjectId;
	private int score;
	private String options;
	private String answers;
	private String description;
	private String adjunct;	//'题目附件地址'
	private String assAdjunct;	// 题目答案附件地址
	private int adjunctType; //附件类型:0:图片,1:audio,2:video
	private int assAdjunctType;
	
	public int getAssAdjunctType() {
		return assAdjunctType;
	}
	public void setAssAdjunctType(int assAdjunctType) {
		this.assAdjunctType = assAdjunctType;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getSuiteId() {
		return suiteId;
	}
	public void setSuiteId(long suiteId) {
		this.suiteId = suiteId;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdjunct() {
		return adjunct;
	}
	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
	}
	public String getAssAdjunct() {
		return assAdjunct;
	}
	public void setAssAdjunct(String assAdjunct) {
		this.assAdjunct = assAdjunct;
	}
	public int getAdjunctType() {
		return adjunctType;
	}
	public void setAdjunctType(int adjunctType) {
		this.adjunctType = adjunctType;
	}
 }

package com.edu.roy.wx.vo;

import com.edu.roy.wx.model.Question;

public class QuestionResultVO extends Question {
	
	private String resultAnswers;
	private int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResultAnswers() {
		return resultAnswers;
	}

	public void setResultAnswers(String resultAnswers) {
		this.resultAnswers = resultAnswers;
	}
	
}

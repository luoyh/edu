package com.edu.roy.wx.vo;

import com.edu.roy.wx.model.Question;

/**
 * 
 * @author luoyh
 * @date Feb 10, 2017
 */
public class WrongQuestionVO extends Question {
	private long wrongId;
	private String wrongAnswers;
	public long getWrongId() {
		return wrongId;
	}
	public void setWrongId(long wrongId) {
		this.wrongId = wrongId;
	}
	public String getWrongAnswers() {
		return wrongAnswers;
	}
	public void setWrongAnswers(String wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}
	
}

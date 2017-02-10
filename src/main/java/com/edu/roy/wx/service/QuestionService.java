package com.edu.roy.wx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.roy.wx.dao.QuestionMapper;
import com.edu.roy.wx.model.Question;

/**
 * 
 * @author luoyh
 * @date Feb 7, 2017
 */
@Service("questionService")
public class QuestionService {
	
	@Autowired
	private QuestionMapper questionMapper;
	
	public List<Question> ofSuite(long suiteId) {
		return questionMapper.loadOfSuite(suiteId);
	}
	
	public Question findById(long id) {
		return questionMapper.findById(id);
	}
	
	public long insert(Question question) {
		questionMapper.insert(question);
		return question.getId();
	}
	
	public void delete(long id) {
		questionMapper.delete(id);
	}
	
	public void update(Question question) {
		questionMapper.update(question);
	}
	
	public void setSuiteId(long suiteId, String ids) {
		questionMapper.setSuiteId(suiteId, ids);
	}

}

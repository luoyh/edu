package com.edu.roy.wx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.roy.wx.dao.SubjectMapper;
import com.edu.roy.wx.model.Subject;

@Service("subjectService")
public class SubjectService {
	
	@Autowired
	private SubjectMapper subjectMapper;
	
	public void insert(Subject subject) {
		subjectMapper.insert(subject);
	}
	
	public List<Subject> list() {
		return subjectMapper.list();
	}
	
	public void update(Subject subject) {
		subjectMapper.update(subject);
	}
	
	public void delete(long id) {
		subjectMapper.delete(id);
	}

}

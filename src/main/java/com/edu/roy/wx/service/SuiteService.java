package com.edu.roy.wx.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.roy.wx.comm.Page;
import com.edu.roy.wx.dao.QuestionMapper;
import com.edu.roy.wx.dao.SuiteMapper;
import com.edu.roy.wx.model.Question;
import com.edu.roy.wx.model.Suite;
import com.edu.roy.wx.tools.JsonTools;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;

@Service("suiteService")
public class SuiteService {
	
	@Autowired
	private SuiteMapper suiteMapper;
	@Autowired
	private QuestionMapper questionMapper;
	
	public Page<Suite> page(int years, int months, int subject, String title, int current, int size) {
		Page<Suite> page = new Page<>();
		Map<String, Object> param = Maps.newHashMap();
		param.put("years", years);
		param.put("months", months);
		param.put("subject", subject);
		param.put("title", title);
		page.setParam(param);
		page.setCurrent(current);
		page.setSize(size);
		page.setData(suiteMapper.page(page));
		return page;
	}
	
	@Transactional
	public void insert(String suites, String questions) {
		Suite suite = JsonTools.readObject(suites, Suite.class);
		List<Question> list = JsonTools.readList(questions, new TypeReference<List<Question>>() {});
		suite.setQuestions(list.size());
		suiteMapper.insert(suite);
		
		Map<String, Object> param = Maps.newHashMap();
		param.put("qstns", list);
		param.put("suiteId", suite.getId());
		questionMapper.inserts(param);
	}
	
	

}

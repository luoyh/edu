package com.edu.roy.wx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.model.Question;
import com.edu.roy.wx.service.QuestionService;

/**
 * 
 * @author luoyh
 * @date Feb 7, 2017
 */
@Controller
@RequestMapping("/admin/question")
public class QuestionController extends BaseController {
	
	@Autowired
	private QuestionService questionService;

	@RequestMapping(value = "/load")
	public ResponseEntity<HttpResult> load(Long id) {
		if (null == id) {
			return bad(HttpResult.ok());
		}
		return ok(new HttpResult(HttpResult.OK, null, questionService.findById(id)));
	}

	@RequestMapping(value = "/of/suite")
	public ResponseEntity<HttpResult> ofsuite(Long suiteId) {
		if (null == suiteId) {
			return bad(HttpResult.ok());
		}
		return ok(new HttpResult(HttpResult.OK, null, questionService.ofSuite(suiteId)));
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> insert(Question question) {
		return ok(new HttpResult(HttpResult.OK, null, questionService.insert(question)));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> update(Question question) {
		questionService.update(question);
		return ok(new HttpResult(HttpResult.OK));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> delete(Long id) {
		if (null == id) {
			return bad(HttpResult.ok());
		}
		questionService.delete(id);
		return ok(new HttpResult(HttpResult.OK));
	}
}

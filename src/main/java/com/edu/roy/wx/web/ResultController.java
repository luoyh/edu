package com.edu.roy.wx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.model.DrillRecord;
import com.edu.roy.wx.service.ResultService;
import com.edu.roy.wx.service.SubjectService;


@Controller
@RequestMapping("/admin/result")
public class ResultController extends BaseController {
	
	@Autowired
	private ResultService resultService;
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping("/go")
	public String goResult(ModelMap model) {
		model.put("subjects", subjectService.list());
		//model.put("data", resultService.pageHandle(0, null, null, null, 1, 20));
		return "/boot/result";
	}
	
	@RequestMapping("/page")
	public ResponseEntity<HttpResult> page(Long subjectId, String suiteTitle, String memberName, String suiteType, Integer current, Integer size) {
		return ok(HttpResult.me(HttpResult.OK, null, resultService.pageHandle(null == subjectId ? 0 : subjectId, suiteTitle, memberName, suiteType, null == current ? 1 : current, null == size ? 20 : size)));
	}
	
	@RequestMapping("/questions")
	public ResponseEntity<HttpResult> qstr(Long resultId, Long suiteId) {
		if (null == resultId || null == suiteId) {
			return bad(HttpResult.me(HttpResult.ERR));
		}
		return ok(HttpResult.me(HttpResult.OK, null, resultService.questionResult(resultId, suiteId)));
	}
	
	@RequestMapping(value = "/question/check", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> check(DrillRecord drillRecord) {
		resultService.checkedQuestion(drillRecord);
		return ok(HttpResult.ok());
	}
	
	@RequestMapping(value = "/over/exam", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> overExam(Long resultId) {
		if (null == resultId) {
			return bad(HttpResult.me(HttpResult.ERR, "invalid parameters."));
		}
		resultService.overExam(resultId);
		return ok(HttpResult.ok());
	}


}
package com.edu.roy.wx.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.model.Subject;
import com.edu.roy.wx.service.SubjectService;

@Controller
@RequestMapping("/admin/subject")
public class SubjectController extends BaseController {
	
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping("/list")
	public ResponseEntity<HttpResult> page() {
		return ok(new HttpResult(HttpResult.OK, null, subjectService.list()));
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> insert(Subject subject) {
		if (StringUtils.isBlank(subject.getName())) {
			return ok(new HttpResult(HttpResult.ERR, "科目名称不能为空"));
		}
		try {
			subjectService.insert(subject);
		} catch(Exception ex) {
			return ok(new HttpResult(HttpResult.ERR, "科目名称已存在"));
		}
		return ok(new HttpResult(HttpResult.OK));
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> delete(Long id) {
		if (null == id) {
			return ok(new HttpResult(HttpResult.ERR, "编号不能为空"));
		}
		try {
			subjectService.delete(id);
		} catch(Exception ex) {
			return ok(new HttpResult(HttpResult.ERR, "删除失败"));
		}
		return ok(new HttpResult(HttpResult.OK));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> update(Subject subject) {
		if (StringUtils.isBlank(subject.getName())) {
			return ok(new HttpResult(HttpResult.ERR, "科目名称不能为空"));
		}
		try {
			subjectService.update(subject);
		} catch(Exception ex) {
			return ok(new HttpResult(HttpResult.ERR, "科目名称已存在"));
		}
		return ok(new HttpResult(HttpResult.OK));
	}
}

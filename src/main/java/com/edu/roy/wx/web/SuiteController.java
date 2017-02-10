package com.edu.roy.wx.web;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.service.QuestionService;
import com.edu.roy.wx.service.SubjectService;
import com.edu.roy.wx.service.SuiteService;
import com.edu.roy.wx.tools.DateTools;
import com.edu.roy.wx.tools.UuidTools;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/admin/suite")
public class SuiteController extends BaseController {
	
	@Autowired
	private SuiteService suiteService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private QuestionService questionService;

	@RequestMapping("/modify")
	public String e(Long id, ModelMap model) {
		if (null != id) {
			//model.put("suite", suiteService.findById(id));
			model.put("suiteId", id);
		} else {
			model.put("suiteId", 0);
		}
		model.put("subs", subjectService.list());
		return "boot/suite_add";
	}

	@RequestMapping("/load/qstn")
	public ResponseEntity<HttpResult> qstn(Long id) {
		if (null == id) {
			return bad(HttpResult.me(HttpResult.OK));
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("suite", suiteService.findById(id));
		map.put("questions", questionService.ofSuite(id));
		return ok(HttpResult.me(HttpResult.OK, null, map));
	}

	@RequestMapping("/load")
	public ResponseEntity<HttpResult> load(Long id) {
		if (null == id) {
			return bad(HttpResult.me(HttpResult.OK));
		}
		return ok(HttpResult.me(HttpResult.OK, null, suiteService.findById(id)));
	}
	
	@RequestMapping("/page")
	public ResponseEntity<HttpResult> page(Integer years, Integer months, Integer subject, String title, Integer current, Integer size) {
		return ok(new HttpResult(HttpResult.OK, null, suiteService.page(null == years ? 0 : years, null == months ? 0 : months, null == subject ? 0 : subject, null == title ? "" : title, null == current ? 1 : current, null == size ? 20 : size)));
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> insert(String suites, String questions) {
		suiteService.insert(suites, questions);
		return ok(new HttpResult(HttpResult.OK));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> update(String suites, String questions) {
		suiteService.update(suites, questions);
		return ok(new HttpResult(HttpResult.OK));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> delete(Long id) {
		if (null == id) {
			return bad(HttpResult.ok());
		}
		suiteService.delete(id);
		return ok(new HttpResult(HttpResult.OK));
	}
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> upload(MultipartFile file) {
		if (null != file) {
			try {
				String months = DateTools.getNow("yyyyMM");
				String path = months + "/" + UuidTools.uuid62String();
				File f = new File(questionPath() + "/" + months);
				if (!f.exists()) {
					f.mkdirs();
				}
				file.transferTo(new File(questionPath() + "/" + path));
				return ok(HttpResult.me(HttpResult.OK, path));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ok(HttpResult.ok());
	}
	@RequestMapping("/by/subject")
	public ResponseEntity<HttpResult> bySubject(Long subjectId) {
		if (null == subjectId) {
			return bad(HttpResult.ok());
		}
		return ok(HttpResult.me(HttpResult.OK, null, suiteService.loadBySubject(subjectId)));
	}
	

}

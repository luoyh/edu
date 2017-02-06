package com.edu.roy.wx.web;

import java.io.File;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.service.SuiteService;
import com.edu.roy.wx.tools.DateTools;
import com.edu.roy.wx.tools.UuidTools;

@Controller
@RequestMapping("/admin/suite")
public class SuiteController extends BaseController {
	
	@Autowired
	private SuiteService suiteService;
	@Autowired
	private ServletContext context;
	
	@RequestMapping("/page")
	public ResponseEntity<HttpResult> page(Integer years, Integer months, Integer subject, String title, Integer current, Integer size) {
		return ok(new HttpResult(HttpResult.OK, null, suiteService.page(null == years ? 0 : years, null == months ? 0 : months, null == subject ? 0 : subject, null == title ? "" : title, null == current ? 1 : current, null == size ? 20 : size)));
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> insert(String suites, String questions) {
		suiteService.insert(suites, questions);
		return ok(new HttpResult(HttpResult.OK));
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> upload(MultipartFile file) {
		if (null != file) {
			Map<?, ?> map = (Map<?, ?>) context.getAttribute("sysConfig");
			try {
				String months = DateTools.getNow("yyyyMM");
				String path = months + "/" + UuidTools.uuid62String();
				File f = new File(map.get("question_image_path") + "/" + months);
				if (!f.exists()) {
					f.mkdirs();
				}
				file.transferTo(new File(map.get("question_image_path") + "/" + path));
				return ok(HttpResult.me(HttpResult.OK, path));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ok(HttpResult.ok());
	}
	

}

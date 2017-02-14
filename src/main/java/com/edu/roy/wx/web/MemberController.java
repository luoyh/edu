package com.edu.roy.wx.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.comm.Page;
import com.edu.roy.wx.model.Member;
import com.edu.roy.wx.service.MemberService;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/admin/member")
public class MemberController extends BaseController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/page")
	public ResponseEntity<HttpResult> page(String mobile, String name, String school, Integer current, Integer size) {
		current = null == current ? 1 : current;
		size = null == size ? 20 : size;
		Map<String, Object> param = Maps.newHashMap();
		if (!StringUtils.isBlank(mobile)) param.put("mobile", mobile);
		param.put("name", name);
		if (!StringUtils.isBlank(school)) param.put("school", school);
		Page<Member> page = memberService.page(param, current, size);
		
		return ResponseEntity.ok(new HttpResult(HttpResult.OK, null, page));
	}

}

package com.edu.roy.wx.web;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.edu.roy.wx.comm.Cons;
import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.model.Member;
import com.edu.roy.wx.service.MemberService;

@Controller
public class BaseController {
	@Autowired
	protected ServletContext context;
	@Autowired
	protected MemberService memberService;
	
	protected ResponseEntity<HttpResult> ok(HttpResult httpResult) {
		return ResponseEntity.ok(httpResult);
	}
	
	protected ResponseEntity<HttpResult> bad(HttpResult httpResult) {
		return ResponseEntity.badRequest().body(httpResult);
	}

	protected Map<?, ?> sysMap() {
		return (Map<?, ?>) context.getAttribute("sysConfig");
	}
	
	protected String questionPath() {
		return sysMap().get("question_image_path").toString();
	}
	
	protected Member currentMember(HttpServletRequest request) {
		try {
			HttpSession hs = request.getSession(false);
			if (null == hs) {
				return null;
			}
			Object target = hs.getAttribute(Cons.SESSION_ID);
			if (null == target) {
				return null;
			}
			String openid = target.toString();
			return memberService.findOfOpenid(openid);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}

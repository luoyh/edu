package com.edu.roy.wx.web;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.edu.roy.wx.bo.WxAccessTokenBO;
import com.edu.roy.wx.cache.Cache;
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
	
	protected Cache cache = Cache.INSTANCE;
	
	protected String obtainAccessToken() {
		Object wxBO = cache.get(Cons.WX_ACCESS_TOKEN_KEY);
		if (null == wxBO) {
			return null;
		}
		if (wxBO instanceof WxAccessTokenBO) {
			if (((WxAccessTokenBO) wxBO).hasExpired()) {
				return null;
			}
			return ((WxAccessTokenBO) wxBO).getAccessToken();
		}
		return null;
	}
	
	protected ResponseEntity<HttpResult> ok(HttpResult httpResult) {
		return ResponseEntity.ok(httpResult);
	}
	
	protected ResponseEntity<HttpResult> bad(HttpResult httpResult) {
		return ResponseEntity.badRequest().body(httpResult);
	}

	protected Map<?, ?> sysMap() {
		return (Map<?, ?>) context.getAttribute(Cons.SYS_CONFIG_CONTEXT_KEY);
	}
	
	protected String questionPath() {
		return sysMap().get(Cons.SysKey.QUESTION_ADJUNCT_PATH.code).toString();
	}
	
	protected String getAppid() {
		return sysMap().get(Cons.SysKey.WX_APPID.code).toString();
	}
	protected String getSecret() {
		return sysMap().get(Cons.SysKey.WX_SECRET.code).toString();
	}
	
	protected boolean testEnvironment() {
		Object t = sysMap().get(Cons.SysKey.GLOBAL_TEST_STATUS.code);
		return null != t && Cons.GLOBAL_STATUS_TEST.equals(t.toString());
	}
	
	private Member _member(HttpServletRequest request) {
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
	
	protected Member currentMember(HttpServletRequest request) {
		Member member = _member(request);
		if (null == member) {
			if (testEnvironment()) {
				member = memberService.findById(0);
				if (null == member) {
					member = new Member();
				}
			} else {
				throw new NullPointerException("not login");
			}
		}
		return member;
	}
}

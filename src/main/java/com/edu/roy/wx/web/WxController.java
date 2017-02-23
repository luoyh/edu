package com.edu.roy.wx.web;

import java.time.Instant;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.roy.wx.bo.WxAccessTokenBO;
import com.edu.roy.wx.cache.CacheValue;
import com.edu.roy.wx.comm.Cons;
import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.model.Member;
import com.edu.roy.wx.model.Suite;
import com.edu.roy.wx.model.WrongResult;
import com.edu.roy.wx.service.ResultService;
import com.edu.roy.wx.service.SuiteService;
import com.edu.roy.wx.tools.HttpTools;
import com.edu.roy.wx.tools.JsonTools;
import com.google.common.collect.Maps;

/**
 * 
 * @author luoyh
 * @date Feb 7, 2017
 */
@Controller
@RequestMapping("/wx")
public class WxController extends BaseController {
	
	@Autowired
	private SuiteService suiteService;
	@Autowired
	private ResultService resultService;
	
	private static Logger log = LoggerFactory.getLogger(WxController.class);
	
	private static Map<String, String> wxUserInfos = Maps.newConcurrentMap();
	
	private String wxOauth2Openid(String code) {
		return new StringBuilder().append("https://api.weixin.qq.com/sns/oauth2/access_token")
		.append("?appid=")
		.append(getAppid())
		.append("&secret=")
		.append(getSecret())
		.append("&code=")
		.append(code)
		.append("&grant_type=authorization_code").toString(); 
	}
	
	private String wxAccessTokenUrl() {
		return new StringBuilder()
				.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=")
				.append(getAppid())
				.append("&secret=")
				.append(getSecret()).toString();
	}
	
	private String wxUserInfoUrl(String accessToken, String openid) {
		return new StringBuilder()
				.append("https://api.weixin.qq.com/cgi-bin/user/info?access_token=")
				.append(accessToken)
				.append("&openid=")
				.append(openid)
				.append("&lang=zh_CN").toString();
	}
	
	@RequestMapping("/index")
	public String index(String code, ModelMap model, HttpServletRequest request) {
		Object s = request.getSession().getAttribute(Cons.SESSION_ID);
		String openid = null;
		if (null == s) {
			log.info("{}", code);
			model.put("timestamp", Instant.now().toEpochMilli());
			//String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code ", args);
			//https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
			String result = HttpTools.doGet(wxOauth2Openid(code));
			Map<?, ?> map = JsonTools.readObject(result, Map.class);
			log.info("result:{}", map);
			try {
				openid = map.get("openid").toString();
			} catch(Exception ex) { // NPE
				return "/app/error";
			}
			// TODO: 获取accessToken
			//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
			// TODO: 获取用户基本信息
			// https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
			// @see https://mp.weixin.qq.com/wiki
		} else {
			openid = s.toString();
		}
		if (StringUtils.isBlank(openid)) {
			return "/app/error";
		}
		Member member = memberService.findOfOpenid(openid);
		
		request.getSession().setAttribute(Cons.SESSION_ID, openid);
		if (null == member) {
			return "/app/register";
		}
		return "redirect:/wx/home";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> register(Member member, HttpServletRequest request) {
		log.info("register {}", member);
		int code = HttpResult.OK;
		String msg = null;
		if (StringUtils.isBlank(member.getName())) {
			code = HttpResult.ERR;
			msg = "姓名不能为空";
		}
		if (StringUtils.isBlank(member.getMobile()) || !Pattern.matches("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", member.getMobile())) {
			code = HttpResult.ERR;
			msg = "手机号码错误";
		}
		if (StringUtils.isBlank(member.getSchool())) {
			code = HttpResult.ERR;
			msg = "学校不能为空";
		}

		Object s = request.getSession().getAttribute(Cons.SESSION_ID);
		member.setOpenid(s.toString());
		
		// set member wechat base info
		String wxUserinfo = wxUserInfos.get(member.getOpenid());
		if (StringUtils.isBlank(wxUserinfo)) {
			String accessToken = obtainAccessToken();
			if (StringUtils.isBlank(accessToken)) { 
				// get access_token
				String tokenResult = HttpTools.doGet(wxAccessTokenUrl());
				Map<?, ?> trMap = JsonTools.readObject(tokenResult, Map.class);
				Object at = trMap.get("access_token");
				if (null == at) {
					code = HttpResult.ERR;
					msg = "获取微信接口access_token失败,请重试";
				} else {
					accessToken = at.toString();
					WxAccessTokenBO watbo = new WxAccessTokenBO();
					watbo.setAccessToken(accessToken);
					watbo.setCreated(System.currentTimeMillis());
					watbo.setExpires(Long.parseLong(trMap.get("expires_in").toString()));
					cache.put(Cons.WX_ACCESS_TOKEN_KEY, new CacheValue<WxAccessTokenBO>(watbo));
				}
			}
			// get user info
			wxUserinfo = HttpTools.doGet(wxUserInfoUrl(accessToken, member.getOpenid()));
			Map<?, ?> itMap = JsonTools.readObject(wxUserinfo, Map.class);
			Object _openid = itMap.get("openid");
			if (null == _openid) {
				code = HttpResult.ERR;
				msg = "获取微信用户基本信息失败,请重试";
			} else {
				wxUserInfos.put(member.getOpenid(), wxUserinfo);
			}
		}
		member.setBaseWxInfo(wxUserinfo);
		
		
		if (code == HttpResult.OK) {
			boolean ok = memberService.insert(member);
			if (!ok) {
				code = HttpResult.ERR;
				msg = "电话号码已被注册";
			}
		}
		
		return ok(HttpResult.me(code, msg));
	}
	
	@RequestMapping("/drill/{id}")
	public String goDrill(@PathVariable("id") Long id, ModelMap model) {
		Suite suite = suiteService.findById(id);
		if (null == suite) {
			return "/app/error";
		}
		model.put("suite", suite);
		return "/app/drill";
	}
	
	@RequestMapping("/exam/{id}")
	public String goExam(@PathVariable("id") Long id, ModelMap model) {
		Suite suite = suiteService.findById(id);
		if (null == suite) {
			return "/app/error";
		}
		model.put("suite", suite);
		return "/app/exam";
	}

	@RequestMapping("/drill2/{id}")
	public String goDrill2(@PathVariable("id") Long id, ModelMap model) {
		Suite suite = suiteService.findById(id);
		if (null == suite) {
			return "/app/error";
		}
		model.put("suite", suite);
		return "/app/drill2";
	}
	@RequestMapping(value = "/drill/submit", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> ds(Integer type, Integer score, Long suiteId, Long subjectId, String data, HttpServletRequest request) {
		Member member = currentMember(request);
		resultService.exam(null == member ? 1 : member.getId(), type, suiteId, subjectId, score, data);
		return ok(HttpResult.ok());
	}
	

	@RequestMapping("/wrong/{id}")
	public String w(@PathVariable("id") long subjectId, ModelMap model) {
		model.put("subjectId", subjectId);
		return "/app/wrong";
	}
	
	@RequestMapping("/wronged")
	public ResponseEntity<HttpResult> wrongedData(Long subjectId, HttpServletRequest request) {
		if (null == subjectId) {
			return bad(HttpResult.me(HttpResult.ERR, "科目编号不能为空"));
		}
		return ok(HttpResult.me(HttpResult.OK, null, resultService.wrongedData(subjectId, currentMember(request).getId())));
	}
	
	@RequestMapping(value = "/wronged/insert", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> wrongInsert(WrongResult wrongResult, HttpServletRequest request) {
		Member member = currentMember(request);
		wrongResult.setMemberId(member.getId());
		resultService.joinWrong(wrongResult);
		return ok(HttpResult.ok());
	}

	@RequestMapping(value = "/wronged/submit", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> wrongSubmit(String ids, HttpServletRequest request) {
		if (StringUtils.isBlank(ids)) {
			return ok(HttpResult.ok());
		}
		resultService.wrongSubmit(ids);
		return ok(HttpResult.ok());
	}
	
	@RequestMapping("/home")
	public String memberHome(ModelMap model, HttpServletRequest request) {
		Member member = currentMember(request);
		model.put("data", resultService.home(member.getId()));
		model.put("member", member);
		return "/app/home";
	}
	
}

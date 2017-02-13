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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.roy.wx.comm.Cons;
import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.model.Member;
import com.edu.roy.wx.model.Suite;
import com.edu.roy.wx.model.WrongResult;
import com.edu.roy.wx.service.ResultService;
import com.edu.roy.wx.service.SuiteService;
import com.edu.roy.wx.tools.HttpTools;
import com.edu.roy.wx.tools.JsonTools;

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
	
	@RequestMapping("/index")
	public String index(String code, ModelMap model, HttpServletRequest request) {
		Object s = request.getSession().getAttribute(Cons.SESSION_ID);
		String openid = null;
		if (null == s) {
			log.info("{}", code);
			model.put("timestamp", Instant.now().toEpochMilli());
			StringBuilder url = new StringBuilder();
			url.append("https://api.weixin.qq.com/sns/oauth2/access_token")
			.append("?appid=wx960b8ad69eeacac5&secret=d4624c36b6795d1d99dcf0547af5443d&code=")
			.append(code).append("&grant_type=authorization_code");
			//https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
			String result = HttpTools.doGet(url.toString());
			Map<?, ?> map = JsonTools.readObject(result, Map.class);
			log.info("result:{}", map);
			try {
				openid = map.get("openid").toString();
			} catch(Exception ex) { // NPE
				return "/app/error";
			}
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
		
		if (code == HttpResult.OK) {
			Object s = request.getSession().getAttribute(Cons.SESSION_ID);
			member.setOpenid(s.toString());
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
		Member member = currentMember(request);
		long memberId = 0;
		if (null != member) {
			memberId = member.getId();
		}
		return ok(HttpResult.me(HttpResult.OK, null, resultService.wrongedData(subjectId, memberId)));
	}
	
	@RequestMapping(value = "/wronged/insert", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> wrongInsert(WrongResult wrongResult, HttpServletRequest request) {
		if (StringUtils.isBlank(ids)) {
			return ok(HttpResult.ok());
		}
		resultService.wrongSubmit(ids);
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
		long memberId = 1;
		if (null != member) {
			memberId = member.getId();
		}
		model.put("data", resultService.home(memberId));
		model.put("member", member);
		return "/app/home";
	}
	
}

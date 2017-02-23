package com.edu.roy.wx.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.roy.wx.comm.Cons;
import com.edu.roy.wx.comm.HttpResult;
import com.edu.roy.wx.service.SysService;
import com.edu.roy.wx.tools.HttpTools;
import com.edu.roy.wx.tools.JsonTools;
import com.edu.roy.wx.tools.UuidTools;

@CrossOrigin
@Controller
public class MianController extends BaseController {

	@Autowired
	private SysService sysService;
	
	private static Logger log = LoggerFactory.getLogger(MianController.class);

	@RequestMapping(value = "/")
	public String m(String username, String password, HttpServletRequest request) {
		return "index";
	}
	
	@RequestMapping(value = "/refresh/config")
	public ResponseEntity<String> refreshConfig(String password, HttpServletRequest request) {
		if (StringUtils.isBlank(password) || !password.equals(sysMap().get(Cons.SysKey.REFRESH_CONFIG_PASSWORD.code))) {
			return ResponseEntity.badRequest().body("u k m?");
		}
		sysService.init();
		return ResponseEntity.<String>ok("success!");
	}

	@RequestMapping(value = "/current/config")
	public ResponseEntity<String> currentConfig(String password, HttpServletRequest request) {
		if (StringUtils.isBlank(password) || !password.equals(sysMap().get(Cons.SysKey.REFRESH_CONFIG_PASSWORD.code))) {
			return ResponseEntity.badRequest().body("u k m?");
		}
		sysService.init();
		return ResponseEntity.<String>ok("success!");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<HttpResult> login(String username, String password, HttpServletRequest request) {
		int code = HttpResult.ERR;
		if ("admin".equals(username) && "administrator".equals(password)) {
			request.getSession().setAttribute(Cons.SESSION_ID, "do");
			code = HttpResult.OK;
		}
		return ok(HttpResult.me(code));
	}
	
	@RequestMapping("/home")
	public String index(@RequestBody(required = false) String body,String code,ModelMap model) {
		log.info("{},{}",body, code);
		model.put("timestamp", Instant.now().toEpochMilli());
		StringBuilder url = new StringBuilder();
		url.append("https://api.weixin.qq.com/sns/oauth2/access_token")
		.append("?appid=wx960b8ad69eeacac5&secret=d4624c36b6795d1d99dcf0547af5443d&code=")
		.append(code).append("&grant_type=authorization_code");
		//https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		String result = HttpTools.doGet(url.toString());
		Map<?, ?> map = JsonTools.readObject(result, Map.class);
		log.info("result:{}", map);
		return "/app/index";
	}

	@RequestMapping("/{path}/go")
	public String g(@PathVariable("path") String path) {
		log.info("go path {}", path);
		return path.replaceAll("\\.", "/");
	}

	@RequestMapping(value = "/man")
	public void man(@RequestBody(required = false) String body,
									String echostr, 
									String signature,
									String timestamp,
									String nonce,
									HttpServletRequest request, HttpServletResponse response) {
		log.info("{},{},{},{},{},{}",request.getMethod(), body, echostr, signature, timestamp, nonce);
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			try (PrintWriter writer = response.getWriter()) {
				writer.print(echostr);
			} catch(IOException ex) {}
		} else {
		}
	}

	@RequestMapping("/down")
	public ResponseEntity<byte[]> down(String path) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", UuidTools.millis62String()); 
		//MediaType.ANY_IMAGE_TYPE.toString()
        headers.setContentType(MediaType.IMAGE_PNG); 
        // TODO:: catch exception
		File file = new File(questionPath() + "/" + path);
		if (file.exists() && !file.isDirectory()) {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} else {
			return null;
		}
	}
	
}

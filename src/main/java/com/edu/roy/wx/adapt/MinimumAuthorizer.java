package com.edu.roy.wx.adapt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.edu.roy.wx.comm.Cons;

/**
 * 
 * @author luoyh
 * @date Feb 7, 2017
 */
public class MinimumAuthorizer extends HandlerInterceptorAdapter {

	private static final String ADMIN = "/admin/";
	private static final String WX = "/wx/";
	private ServletContext context;
	
	public MinimumAuthorizer(ServletContext context) {
		this.context = context;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map<?, ?> config = (Map<?, ?>) context.getAttribute("sysConfig");
		if (null != config.get("global_test_status") && "test".equals(config.get("global_test_status"))) {
			return super.preHandle(request, response, handler); 
		}
		String path = request.getServletPath();
		if (path.startsWith(ADMIN)) {
			HttpSession hs = request.getSession(false);
			if (null == hs || hs.getAttribute(Cons.SESSION_ID) == null) {
				return login(request, response);
			}
		}
		if (path.startsWith(WX) && !path.equals("/wx/index")) {
			HttpSession hs = request.getSession(false);
			if (null == hs || hs.getAttribute(Cons.SESSION_ID) == null) {
				return wxerr(request, response);
			}
		}
		return super.preHandle(request, response, handler);
	}
	
	private boolean wxerr(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			response.setHeader("SessionStatus", "TimeOut");
			return false;
		}
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		writer.write("<script type=\"text/javascript\">");
		writer.write("window.top.location.href=\"" + request.getContextPath() + "/app.error/go\"");
		writer.write("</script>");
		writer.flush();
		writer.close();
		return false;
	}
	
	private boolean login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			response.setHeader("SessionStatus", "TimeOut");
			response.setStatus(444);
			return false;
		}
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		writer.write("<script type=\"text/javascript\">");
		writer.write("window.top.location.href=\"" + request.getContextPath() + "/login/go\"");
		writer.write("</script>");
		writer.flush();
		writer.close();
		return false;
	}
	
	

}

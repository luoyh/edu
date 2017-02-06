package com.edu.roy.wx.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.edu.roy.wx.comm.HttpResult;

@Controller
public class BaseController {
	
	protected ResponseEntity<HttpResult> ok(HttpResult httpResult) {
		return ResponseEntity.ok(httpResult);
	}

}

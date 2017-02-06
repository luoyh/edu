package com.edu.roy.wx.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.roy.wx.dao.SysMapper;
import com.edu.roy.wx.model.Sys;
import com.google.common.collect.Maps;

/**
 * 
 * @author luoyh
 * @date Feb 6, 2017
 */
@Service("sysService")
public class SysService {
	@Autowired
	private SysMapper sysMapper;
	@Autowired
	private ServletContext context;

	@PostConstruct
	public void init() {
		List<Sys> list = sysMapper.list();
		Map<String, String> sysConfig = Maps.newHashMap();
		for (Sys sys : list) {
			sysConfig.put(sys.getSysKey(), sys.getSysValue());
		}
		context.setAttribute("sysConfig", sysConfig);
	}

}

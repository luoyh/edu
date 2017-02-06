package com.edu.roy.wx.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.roy.wx.comm.Page;
import com.edu.roy.wx.dao.MemberMapper;
import com.edu.roy.wx.model.Member;

@Service("memberService")
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	public void insert(Member member) {
		memberMapper.insert(member);
	}
	
	public List<Member> list() {
		return memberMapper.list();
	}
	
	public Page<Member> page(Map<String, Object> param, int current, int size) {
		Page<Member> page = new Page<>();
		page.setParam(param);
		page.setCurrent(current);
		page.setSize(size);
		page.setData(memberMapper.page(page));
		
		return page;
	}

}

package com.edu.roy.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.edu.roy.wx.comm.Page;
import com.edu.roy.wx.model.Member;

public interface MemberMapper {
	
	@Select("select * from member")
	List<Member> list();
	
	@Insert("insert into member(openid,mobile,name,age,school,sex,gmt_created,gmt_modified) values(#{openid},#{mobile},#{name},#{age},#{school},#{sex},now(),now())")
	void insert(Member member);

	List<Member> page(Page<Member> page);

}
package com.edu.roy.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.edu.roy.wx.model.Sys;

public interface SysMapper {
	
	@Select("select * from sys")
	List<Sys> list();
	
	@Insert("insert into sys(sys_key,sys_value,gmt_created,gmt_modified) values(#{sysKey},#{sysValue},now(),now())")
	void insert(Sys sys);
	
	@Update("update sys set sys_key=#{sysKey},sys_value=#{sys_value},gmt_modified=now() where id=#{id}")
	void update(Sys sys);

}

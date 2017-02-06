package com.edu.roy.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.edu.roy.wx.model.Subject;

public interface SubjectMapper {
	
	@Select("select * from subject")
	List<Subject> list();
	
	@Insert("insert into subject(`name`,gmt_created,gmt_modified) values(#{name},now(),now())")
	void insert(Subject subject);
	
	@Update("update subject set `name`=#{name},gmt_modified=now() where id=#{id}")
	void update(Subject subject);
	
	@Delete({"delete from subject where id=#{id};",
			"delete from drill_record where subject_id=#{id};",
			"delete from drill_result where subject_id=#{id};",
			"delete from suite where subject_id=#{id};",
			"delete from question where subject_id=#{id};"})
	void delete(long id);
	
}
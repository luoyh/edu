package com.edu.roy.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.edu.roy.wx.comm.Page;
import com.edu.roy.wx.model.Suite;

public interface SuiteMapper {
	
	List<Suite> page(Page<Suite> page);
	
	@Insert("insert into suite(title,years,months,subject_id,questions,score,timing,gmt_created,gmt_modified) values(#{title},#{years},#{months},#{subjectId},#{questions},#{score},#{timing},now(),now())")
	@SelectKey(statement = "select last_insert_id()", keyProperty = "id", resultType = long.class, before = false)
	void insert(Suite suite);
	
	@Select("select * from suite where id=#{id}")
	Suite findById(long id);
	
	@Update("update suite set title=#{title},years=#{years},months=#{months},subject_id=#{subjectId},questions=#{questions},score=#{score},timing=#{timing},gmt_modified=now() where id=#{id}")
	void update(Suite suite);
	
	@Delete("delete from suite where id=#{id};delete from question where suite_id=#{id};")
	void delete(long id);
	
	@Select("select * from suite where subject_id=#{subjectId}")
	List<Suite> loadBySubject(long subjectId);

}

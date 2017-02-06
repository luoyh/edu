package com.edu.roy.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.edu.roy.wx.comm.Page;
import com.edu.roy.wx.model.Suite;

public interface SuiteMapper {
	
	List<Suite> page(Page<Suite> page);
	
	@Insert("insert into suite(title,years,months,subject_id,questions,gmt_created,gmt_modified) values(#{title},#{years},#{months},#{subjectId},#{questions},now(),now())")
	@SelectKey(statement = "select last_insert_id()", keyProperty = "id", resultType = long.class, before = false)
	void insert(Suite suite);

}

package com.edu.roy.wx.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;

import com.edu.roy.wx.model.Question;

public interface QuestionMapper {
	
	@Insert("insert into question(sort,title,type,suite_id,subject_id,score,options,answers,description,images,ass_images,gmt_created,gmt_modified) values("
			+ "#{sort},#{title},#{type},#{suiteId},#{subjectId},#{score},#{options},#{answers},#{description},#{images},#{assImages},now(),now())")
	void insert(Question question);

	void inserts(Map<String, Object> param);
}

package com.edu.roy.wx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.edu.roy.wx.model.Question;

public interface QuestionMapper {
	
	@Insert("insert into question(sort,title,type,suite_id,subject_id,score,options,answers,description,images,ass_images,gmt_created,gmt_modified) values("
			+ "#{sort},#{title},#{type},#{suiteId},#{subjectId},#{score},#{options},#{answers},#{description},#{images},#{assImages},now(),now())")
	@SelectKey(statement = "select last_insert_id()", keyProperty = "id", resultType = long.class, before = false)
	void insert(Question question);

	void inserts(Map<String, Object> param);
	
	@Delete("delete from question where id=#{id}")
	void delete(long id);
	
	@Update("update question set sort=#{sort},title=#{title},`type`=#{type},suite_id=#{suiteId},subject_id=#{subjectId},score=#{score},options=#{options},answers=#{answers},description=#{description},images=#{images},ass_images=#{assImages},gmt_modified=now() where id=#{id}")
	void update(Question question);
	
	@Update("update question set suite_id=#{suiteId} where id in (${ids})")
	void setSuiteId(@Param("suiteId") long suiteId, @Param("ids") String ids);
	
	@Select("select * from question where id=#{id}")
	Question findById(long id);
	
	@Select("select * from question where suite_id=#{suiteId} order by sort")
	List<Question> loadOfSuite(long suiteId);
}

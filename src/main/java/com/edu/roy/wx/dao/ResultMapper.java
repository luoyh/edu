package com.edu.roy.wx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.edu.roy.wx.vo.HomeResultVO;
import com.edu.roy.wx.vo.WrongQuestionVO;

/**
 * 
 * @author luoyh
 * @date Feb 9, 2017
 */
public interface ResultMapper {

	void examed(Map<String, Object> param);
	
	@Insert("insert into drill_result(member_id, subject_id, suite_id, type, target_id, score, gmt_created, gmt_modified) values(#{memberId}, #{subjectId}, #{suiteId}, #{type}, #{targetId}, #{score}, now(), now())")
	@Options(useGeneratedKeys = false)
	void insertResult(@Param("memberId") long memberId, @Param("subjectId") long subjectId, @Param("suiteId") long suiteId, @Param("type") int type, @Param("targetId") long targetId, @Param("score") int score);
	
	void wrongsSave(Map<String, Object> param);
	
	@Select("select b.*,a.id wrongId,a.answers wrongAnswers from wrong_result a join question b on a.question_id=b.id where a.subject_id=#{subjectId} and a.member_id=#{memberId} and a.cnd=0")
	List<WrongQuestionVO> wrongedData(@Param("subjectId") long subjectId, @Param("memberId") long memberId);
	
	@Update("update wrong_result set cnd=1 where id in (${ids})")
	void updateWronged(@Param("ids") String ids);
	
	@Select("select a.*,b.title suiteTitle,c.`name` subjectName,(select count(*) from drill_record where member_id=1) as total,(select count(*) from wrong_result where member_id=1) as wrongs from drill_result a join suite b on a.suite_id=b.id join `subject` c on a.subject_id=c.id  where a.member_id=#{memberId} order by a.gmt_created desc")
	List<HomeResultVO> home(long memberId);
}
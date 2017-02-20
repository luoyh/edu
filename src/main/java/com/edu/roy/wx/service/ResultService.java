package com.edu.roy.wx.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.roy.wx.comm.Page;
import com.edu.roy.wx.dao.ResultMapper;
import com.edu.roy.wx.model.DrillRecord;
import com.edu.roy.wx.model.DrillResult;
import com.edu.roy.wx.model.WrongResult;
import com.edu.roy.wx.tools.JsonTools;
import com.edu.roy.wx.vo.HomeResultVO;
import com.edu.roy.wx.vo.QuestionResultVO;
import com.edu.roy.wx.vo.ResultVO;
import com.edu.roy.wx.vo.WrongQuestionVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author luoyh
 * @date Feb 9, 2017
 */
@Service("resultService")
public class ResultService {
	
	@Autowired
	private ResultMapper resultMapper;
	
	/**
	 * 
	 * @param memberId
	 * @param type 
	 * @param suiteId
	 * @param subjectId
	 * @param score
	 * @param data
	 * <br><br><b>
	 * Feb 10, 2017 @luoyh </b>
	 * @see com.edu.roy.wx.model.DrillResult.Type
	 */
	@Transactional
	public void exam(long memberId, int type, long suiteId, long subjectId, int score, String data) {
		List<DrillRecord> ldr = JsonTools.readList(data, new TypeReference<List<DrillRecord>>() {});
		List<WrongResult> lwr = Lists.newArrayList();
		for (DrillRecord dr : ldr) {
			if (dr.getScore() == 0 && dr.getStatus() == 1) {
				WrongResult wr = new WrongResult();
				wr.setAnswers(dr.getAnswers());
				wr.setQuestionId(dr.getQuestionId());
				lwr.add(wr);
			}
		}

		// DrillResult.Type.SUITE.code
		DrillResult dr = new DrillResult();
		dr.setMemberId(memberId);
		dr.setSubjectId(subjectId);
		dr.setSuiteId(suiteId);
		dr.setType(type);
		dr.setTargetId(suiteId);
		dr.setScore(score);
		//memberId, subjectId, suiteId, type, suiteId, score
		resultMapper.insertResult(dr);
		
		Map<String, Object> param = Maps.newHashMap();
		param.put("memberId", memberId);
		param.put("suiteId", suiteId);
		param.put("subjectId", subjectId);
		param.put("data", ldr);
		param.put("resultId", dr.getId());
		resultMapper.examed(param);
		
		if (!lwr.isEmpty()) {
			param.put("data", lwr);
			resultMapper.wrongsSave(param);
		}
	}
	
	public List<WrongQuestionVO> wrongedData(long subjectId, long memberId) {
		return resultMapper.wrongedData(subjectId, memberId);
	}
	
	public void wrongSubmit(String ids) {
		if (Pattern.matches("(\\d+,?)+", ids)) {
			resultMapper.updateWronged(ids);
		}
	}
	
	public void joinWrong(WrongResult wrongResult) {
		resultMapper.joinWrong(wrongResult);
	}
	
	public List<HomeResultVO> home(long memberId) {
		return resultMapper.home(memberId);
	}

	public Page<ResultVO> pageHandle(long subjectId, String suiteTitle, String memberName, String suiteType, int current, int size) {
		Page<ResultVO> page = new Page<>();
		page.setAsc(false);
		page.setOrder("gmt_created");
		page.setCurrent(current);
		page.setSize(size);
		Map<String, Object> param = Maps.newHashMap();
		if (subjectId > 0) {
			param.put("subjectId", subjectId);
		}
		if (!StringUtils.isBlank(suiteTitle)) {
			param.put("suiteTitle", suiteTitle);
		}
		if (!StringUtils.isBlank(memberName)) {
			param.put("memberName", memberName);
		}
		if (!StringUtils.isBlank(suiteType)) {
			param.put("suiteType", suiteType);
		}
		page.setParam(param);
		page.setData(resultMapper.page(page));
		return page;
	}
	
	public List<QuestionResultVO> questionResult(long resultId, long suiteId) {
		return resultMapper.questionResult(resultId, suiteId);
	}
	
	public void checkedQuestion(DrillRecord drillRecord) {
		//#{memberId},#{resultId},#{type},#{result},#{subjectId},#{suiteId},#{questionId},#{answers},#{score}
		//DrillRecord drillRecord = new DrillRecord();
		resultMapper.checkedQuestion(drillRecord);
		resultMapper.refreshResultScore(drillRecord.getResultId());
	}
	
	public void overExam(long resultId) {
		resultMapper.checkedResult(resultId);
	}
	
}

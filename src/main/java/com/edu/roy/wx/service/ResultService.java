package com.edu.roy.wx.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.roy.wx.dao.ResultMapper;
import com.edu.roy.wx.model.DrillRecord;
import com.edu.roy.wx.model.WrongResult;
import com.edu.roy.wx.tools.JsonTools;
import com.edu.roy.wx.vo.HomeResultVO;
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
			if (dr.getResult() != DrillRecord.Result.RIGHT.code && dr.getResult() != DrillRecord.Result.INCERTITUDE.code) {
				WrongResult wr = new WrongResult();
				wr.setAnswers(dr.getAnswers());
				wr.setQuestionId(dr.getQuestionId());
				lwr.add(wr);
			}
		}
		Map<String, Object> param = Maps.newHashMap();
		param.put("memberId", memberId);
		param.put("suiteId", suiteId);
		param.put("subjectId", subjectId);
		param.put("data", ldr);
		resultMapper.examed(param);
		
		if (!lwr.isEmpty()) {
			param.put("data", lwr);
			resultMapper.wrongsSave(param);
		}
		// DrillResult.Type.SUITE.code
		resultMapper.insertResult(memberId, subjectId, suiteId, type, suiteId, score);
	}
	
	public List<WrongQuestionVO> wrongedData(long subjectId, long memberId) {
		return resultMapper.wrongedData(subjectId, memberId);
	}
	
	public void wrongSubmit(String ids) {
		if (Pattern.matches("(\\d+,?)+", ids)) {
			resultMapper.updateWronged(ids);
		}
	}
	
	public List<HomeResultVO> home(long memberId) {
		return resultMapper.home(memberId);
	}

}

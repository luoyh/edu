package com.edu.roy.wx.model;

import com.edu.roy.wx.comm.Entity;

/**
 * 
 * @author luoyh
 * @date Feb 9, 2017
 */
public class DrillResult extends Entity {
	
	public static enum Type {
		DRILL(-2), // 练习
		EXAM(-1),  // 考试
		
		ALL(0),    // 顺序练习
		SINGLE(1), // 单选
		MULTI(2),  // 多选
		JUDGE(3),  // 判断
		SOLVE(4),  // 解答题
		OTHER(5)   // 其它
		;
		public final int code;
		private Type(int code) {
			this.code = code;
		}

		public static boolean in(int code) {
			for (Type type : Type.values()) {
				if (type.code == code) {
					return true;
				}
			}
			return false;
		}
		
	}
	
	private long memberId;
	private long subjectId;
	private long suiteId; // type < 0 is suiteId
	private int type;	// -2:drill,-1:exam,0:all,1:single,2:multi,3:judge,4:solve,5:other
	private long targetId; // type < 0 is suiteId else questionId
	private int score;
	
	
	public long getSuiteId() {
		return suiteId;
	}
	public void setSuiteId(long suiteId) {
		this.suiteId = suiteId;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}

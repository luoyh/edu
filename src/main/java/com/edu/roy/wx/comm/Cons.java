package com.edu.roy.wx.comm;

/**
 * 
 * @author luoyh
 * @date Feb 7, 2017
 */
public class Cons {
	public static final String SESSION_ID = "___edU_sID";
	public static final String SYS_CONFIG_CONTEXT_KEY = "sys_config_context";
	public static final String GLOBAL_STATUS_TEST = "test";
	public static final String WX_ACCESS_TOKEN_KEY = "wx_access_token_key";
	
	
	public static enum SysKey {
		WX_APPID("wx_appid"),
		WX_SECRET("wx_secret"),
		GLOBAL_TEST_STATUS("global_test_status"),
		QUESTION_ADJUNCT_PATH("question_adjunct_path"),
		REFRESH_CONFIG_PASSWORD("refresh_config_password")
		;
		
		public final String code;
		private SysKey(String code) {
			this.code = code;
		}
	}

}

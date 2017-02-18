package com.edu.roy.wx.bo;

/**
 * 
 * @author luoyh
 * @date Feb 17, 2017
 */
public class WxAccessTokenBO {
	
	private String accessToken;
	private long expires;
	private long created;
	
	public boolean hasExpired() {
		return System.currentTimeMillis() - created >= expires;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public long getExpires() {
		return expires;
	}
	public void setExpires(long expires) {
		this.expires = expires;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	
	

}

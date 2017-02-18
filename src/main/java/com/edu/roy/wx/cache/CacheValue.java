package com.edu.roy.wx.cache;

/**
 * 
 * @author luoyh
 * @date Feb 7, 2017
 * @param <T>
 */
public class CacheValue<T> {

	private T val;
	private long timestamp = -1;
	private boolean live = false;

	public CacheValue() {
	}

	public CacheValue(T val) {
		this.val = val;
		this.timestamp = System.currentTimeMillis();
		this.live = true;
	}

	public CacheValue(T val, boolean refuse, long timestamp) {
		this.val = val;
		this.timestamp = timestamp;
		this.live = refuse;
	}

	public void refresh() {
		this.live = true;
		this.timestamp = System.currentTimeMillis();
	}

	public T getVal() {
		return val;
	}

	public void setVal(T val) {
		this.val = val;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

}

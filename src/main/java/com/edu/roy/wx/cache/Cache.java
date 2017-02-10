package com.edu.roy.wx.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 
 * @author luoyh
 * @date Feb 7, 2017
 */
public enum Cache {

	INSTANCE;

	private LoadingCache<String, CacheValue<?>> cache;

	private Cache() {
		cache = CacheBuilder.newBuilder().concurrencyLevel(10).build(new CacheLoader<String, CacheValue<?>>() {
			@Override
			public CacheValue<?> load(String key) throws Exception {
				return new CacheValue<Void>();
			}
		});
	}

	public <T> void put(String key, CacheValue<T> value) {
		cache.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> CacheValue<T> get(String key) {
		return (CacheValue<T>) cache.getIfPresent(key);
	}

	public <T> void refresh(CacheValue<T> value) {
		value.refresh();
	}

	public void remove(String key) {
		cache.invalidate(key);
	}

	public void clean() {
		cache.cleanUp();
	}

}

package com.jgonet.memcached;

import java.util.List;


public interface IMemcachedManager{
	Object get(String key);
	
	String getString(String key);

	boolean add(String key, Object value);
	
	boolean add(String key, String value);

	boolean add(String key, Object value, long expireInMilliSeconds);

	boolean delete(String key);

	boolean replace(String key, Object value);

	boolean replace(String key, Object value, long expireInMilliSeconds);

	public void expire(String key, long expireInMilliSeconds);
	
	
	public long incr(String key, int seconds);
}

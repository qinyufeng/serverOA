package com.jgonet.dao;

import java.util.Map;

import com.jgonet.pojo.user.User;

public interface UserDao {
	public Map<String,Object> findById(Long id);
	
	public User checkUser(String name);
}

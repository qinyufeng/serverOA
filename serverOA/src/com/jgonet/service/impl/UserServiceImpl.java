package com.jgonet.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgonet.dao.UserDao;
import com.jgonet.service.UseService;
@Service
public class UserServiceImpl implements UseService{
	@Autowired
	private UserDao userDao;
	@Override
	public Boolean findById(Long id) {
		Map<String, Object> params = userDao.findById(id);
		String CNT = params.get("CNT").toString()==null?"":params.get("CNT").toString();
		if(Integer.valueOf(CNT)>0){
			return true;
		}
		return false;
	}

}

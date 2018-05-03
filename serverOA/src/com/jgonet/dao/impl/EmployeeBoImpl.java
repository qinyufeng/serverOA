package com.jgonet.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jgonet.dao.EmployeeBo;
import com.jgonet.dao.EmployeeDao;

@Repository("employeeBo")
public class EmployeeBoImpl implements EmployeeBo{
	@Resource
	private EmployeeDao employeeDao;
	
	@Override
	public List<Map<String,Object>> findAllEmployeesByDept() throws Exception{
		return employeeDao.findAllEmployeesByDept();
	}

	@Override
	public List<Map<String, Object>> findAllemp(String department) {
		return employeeDao.findAllEmp(department);
	}

	@Override
	public Map<String, Object> isBos() {
		// TODO Auto-generated method stub
		return employeeDao.isBos();
	}

}


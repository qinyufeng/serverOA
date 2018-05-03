package com.jgonet.dao;

import java.util.List;
import java.util.Map;

public interface EmployeeBo {
	public List<Map<String,Object>> findAllEmployeesByDept() throws Exception;

	public List<Map<String,Object>> findAllemp(String department);

	public Map<String,Object> isBos();
}

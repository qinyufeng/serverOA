package com.jgonet.dao;

import java.util.List;
import java.util.Map;

import com.jgonet.pojo.department.Department;
import com.jgonet.pojo.employee.Employee;

public interface EmployeeDao {
	public Employee checkEmployee(String spa);
    public List<Map<String, Object>> getDepartments();
    
	public List<Map<String,Object>> findAllEmployeesByDept()throws Exception;

	public List<Map<String, Object>> findAllEmp(String department);

	public Map<String, Object> isBos();
}

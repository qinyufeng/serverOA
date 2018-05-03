package com.jgonet.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jgonet.dao.EmployeeDao;
import com.jgonet.pojo.department.Department;
import com.jgonet.pojo.employee.Employee;
import com.jgonet.pojo.user.User;
@Repository
public class EmployeeDaoImpl implements EmployeeDao{

	@Resource
	private JdbcTemplate ptJdbcTemplatewrite;
	
	@Override
	public Employee checkEmployee(String spa) {
		//步骤1:通过name查询用户名是否存在
		try {
			String sql ="select * from oa_employee where spa=?";
			Employee employee = (Employee) ptJdbcTemplatewrite.queryForObject(sql, new Object[]{spa},new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Employee employee =new Employee();
					employee.setName(rs.getString("name"));
					employee.setPassword(rs.getString("password"));
					employee.setMobile(rs.getString("mobile"));
					employee.setPost(rs.getString("post"));
					employee.setSex(rs.getInt("sex"));
					employee.setSpa(rs.getString("spa"));
					employee.setWxid(rs.getString("wxid"));
					employee.setSuperior(rs.getString("superior"));
					return employee;
				}  
				  
		    });
			System.out.println("========>"+employee);
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getDepartments() {
		try {
			String sql="select * from oa_department";		
			List<Map<String, Object>> departments=ptJdbcTemplatewrite.queryForList(sql);
			System.out.println("========>"+departments);
			return departments;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> findAllEmployeesByDept() throws Exception{
		String sql="select d.department,d.name from " +
				"oa_department d left join oa_employee e on d.department = e.department " +
				"group by d.department,d.name";
		return ptJdbcTemplatewrite.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findAllEmp(String department) {
		String sql ="select spa,name from oa_employee where department="+department;
		return ptJdbcTemplatewrite.queryForList(sql);
	}

	@Override
	public Map<String, Object> isBos() {
		String sql = "select spa,name from oa_employee where manager is null";
		return ptJdbcTemplatewrite.queryForMap(sql);
	}

}

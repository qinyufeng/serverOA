package com.jgonet.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jgonet.dao.UserDao;
import com.jgonet.pojo.user.User;
import com.mysql.jdbc.PreparedStatement;
@Repository
public class UserDaoImpl implements UserDao{
	@Resource
	private JdbcTemplate ptJdbcTemplatewrite;
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> findById(Long id) {
		try {
			String sql="select count(1) as CNT from user where id=?";
			System.out.println(ptJdbcTemplatewrite==null);
			return ptJdbcTemplatewrite.queryForMap(sql, new Object[]{id});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public User checkUser(String name) {
		//步骤1:通过name查询用户名是否存在
		try {
			String sql ="select * from user where name=?";
			User user = (User) ptJdbcTemplatewrite.queryForObject(sql, new Object[]{name},new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					User user =new User();
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					return user;
				}  
				  
		    });
			System.out.println("========>"+user);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

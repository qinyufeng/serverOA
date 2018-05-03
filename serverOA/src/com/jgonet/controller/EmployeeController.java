package com.jgonet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jgonet.dao.EmployeeBo;
import com.jgonet.dao.EmployeeDao;
import com.jgonet.pojo.employee.Employee;
import com.jgonet.util.DoAjax;
import com.jgonet.util.ResultObj;
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EmployeeBo employeeBo;
	private Logger logger = Logger.getLogger(EmployeeController.class);
	
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response){
		String jsonStr=request.getParameter("reqData");
		JSONObject jsonObj=new JSONObject();
		jsonObj=JSONObject.fromObject(jsonStr);
		String name=jsonObj.getString("name");
		//System.out.println(name);
		String password=jsonObj.getString("password");
		Employee employee=this.employeeDao.checkEmployee(name);
		JSONObject objData=new JSONObject();
		objData.put("user", employee);
		ResultObj result = new ResultObj();
		if(employee == null){
			//表示用户名不存在，登录失败,登录失败1，登录成功0 
			result.setInfo("用户名不存在!");
			result.setCode(1);
			objData.put("result", result);
		}else{
			//比较密码是否相同，如果密码不同表示密码错误也是登录失败
			if(!password.equals(employee.getPassword())){
				result.setInfo("密码错误!");
				result.setCode(2);
				objData.put("result", result);
			}else{
				//登录成功
				result.setInfo("登录成功!");
				result.setCode(0);
				objData.put("result", result);
			}
		}
		//记住这里不管怎么用都有一个DoAjax的返回流到相应体中ong
		DoAjax.doAjax(response, objData, null);
		return;
	}
    @RequestMapping(value="/depart.do",method=RequestMethod.POST)
	public void getDepartments(HttpServletRequest request,HttpServletResponse response){
		JSONObject objData=new JSONObject();
		List<Map<String, Object>> department=this.employeeDao.getDepartments();

		objData.put("result", department);
		DoAjax.doAjax(response, objData, null);
    }
    

	/**
	 * 部门分组查询出所有的员工表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/employessByDept.do",method=RequestMethod.POST)
	public void employessByDept(HttpServletRequest request,HttpServletResponse response){
		logger.info("*******部门分组查询出所有的员工表开始了*******");
		try {
			List<Map<String, Object>> allemployees = employeeBo.findAllEmployeesByDept();
			JSONObject json = new JSONObject();
			ResultObj result = new ResultObj();
			List resultList = new ArrayList();
			List<Map<String, Object>> allemp = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : allemployees) {
				String department = map.get("department").toString();
				String name = map.get("name").toString();
				if(department!=null){
					allemp = employeeBo.findAllemp(department);
					map.put("empList", allemp);
					resultList.add(map);
				}
			}
			json.put("result", resultList);
			DoAjax.doAjax(response, json, null);
		} catch (Exception e) {
			logger.info("异常出错:"+e);
		}
	}
	@RequestMapping(value="/isbos.do",method=RequestMethod.POST)
	public void isBos(HttpServletRequest request,HttpServletResponse response){
		logger.info("*******isBos start***********");
		try {
			JSONObject json =new JSONObject();
			Map<String, Object> bosMap = employeeBo.isBos();
			json.put("bosMap", bosMap);
			DoAjax.doAjax(response, json, null);
		} catch (Exception e) {
			logger.info("异常出错:"+e);
		}
	}
}

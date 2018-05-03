package com.jgonet.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jgonet.dao.UserDao;
import com.jgonet.pojo.user.User;
import com.jgonet.service.UseService;
import com.jgonet.util.DoAjax;
import com.jgonet.util.ResultObj;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private UseService useService;
	@Autowired
	private UserDao userDao;

	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response){

		//在这里如果要提示信息,这里系统专门写了一个Resultobj返回信息
		String jsonStr=request.getParameter("reqData");
		JSONObject jsonObj=new JSONObject();
		jsonObj=JSONObject.fromObject(jsonStr);
		String name=jsonObj.getString("name");
		//System.out.println(name);
		String password=jsonObj.getString("password");
		User user = this.userDao.checkUser(name);
		JSONObject objData=new JSONObject();
		ResultObj result = new ResultObj();
		if(user == null){
			//表示用户名不存在，登录失败,登录失败1，登录成功0 
			result.setInfo("用户名不存在!");
			result.setCode(1);
			objData.put("result", result);
		}else{
			//比较密码是否相同，如果密码不同表示密码错误也是登录失败
			if(!password.equals(user.getPassword())){
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
	/**
	 * 文件上传 
	 */
	@RequestMapping(value="/upload.do",method=RequestMethod.POST)
	public void upload(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("file")MultipartFile file){
		JSONObject obj =new JSONObject();
		try {
			if(!file.isEmpty()) {
	            //上传文件路径
	            String path = request.getServletContext().getRealPath("/images/");
	            //上传文件名
	            String filename = file.getOriginalFilename();
	            File filepath = new File(path,filename);
	            //判断路径是否存在，如果不存在就创建一个
	            if (!filepath.getParentFile().exists()) { 
	                filepath.getParentFile().mkdirs();
	            }
	            System.out.println("1111++++"+(path + File.separator + filename));
	            //将上传文件保存到一个目标文件当中
	            file.transferTo(new File(path + File.separator + filename));
	            obj.put("upload", "success");
	        } else {
	        	obj.put("upload", "false");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DoAjax.doAjax(response, obj, null);
	}
	@RequestMapping("/new.do")
	public void test(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj =new JSONObject();
		Boolean flag = useService.findById(1L);
		List list = new ArrayList();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		obj.put("result", flag);
		obj.put("newList", list);
		DoAjax.doAjax(response, obj, null);
	}
}

package com.example.demo.login.controller;


import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.login.domain.UserBean;
import com.example.demo.login.service.UserService;
import com.example.demo.tool.EncodeUtil;


@Controller
@CrossOrigin
public class UserLoginController {
	
	@Autowired
	private UserService service;
	
	@SuppressWarnings("static-access")
	@PostMapping("/hospital/checkUserAndPass")
	public @ResponseBody Map<String, Object> checkUserAndPass(HttpServletRequest request,
            HttpServletResponse response){
		HashMap<String,Object> map = new HashMap<String,Object>();
		int id = -1 ;
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("codeValidate");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = new EncodeUtil().encodeBase64String(password);
		UserBean user = new UserBean();
		user.setUsername(username);
		user.setPassword(password);
		user.setIsonline(0);
		UserBean userkey= service.select(user);
		if(userkey != null)
			id = userkey.getId();
		map.put("id", id);
	//	service.test();
		if(userkey != null&&username.equals(userkey.getUsername())&&password.equals(userkey.getPassword())){
			map.put("message", "输入正确");
			map.put("state", 1);
			session.setAttribute("userId",  id+"");
		}else{
			map.put("message", "用户名或密码错误，请重新输入");
			map.put("state", -1);
		}
		String yzm = request.getParameter("yzm");
		if(!yzm.toLowerCase().equals(code.toLowerCase())){
			map.put("message", "验证码错误，请重新输入");
			map.put("state", 0);
		}
//		Cookie cookie = new Cookie("userId", id+"");
//		//设置为负值的话，则为浏览器进程Cookie(内存中保存)，关闭浏览器就失效。
//		cookie.setMaxAge(-1);
//		response.addCookie(cookie);
		return map;
	}
	
	
}

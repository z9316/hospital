package com.example.demo.register.controller;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.login.domain.UserBean;
import com.example.demo.login.service.FeedbackService;
import com.example.demo.login.service.UserRegisterService;
import com.example.demo.login.service.UserService;
import com.example.demo.parameter.domain.ParameterBean;
import com.example.demo.parameter.service.HerbParameterService;
import com.example.demo.tool.EncodeUtil;

@Controller
@CrossOrigin
public class UserRegisterController {
	
	static final char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	@Autowired
	private FeedbackService feedservice;
	
	@Autowired
	private UserRegisterService registerservice;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private HerbParameterService pservice;

	@SuppressWarnings("static-access")
	@PostMapping("/hospital/getEmailYZM")
	public @ResponseBody Map<String,Object> getEmailYZM(HttpServletRequest request,
            HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuilder builderCode = new StringBuilder();
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		UserBean bean = new UserBean();
		bean.setEmail(email);
		bean.setUsername(username);
		UserBean userkey= service.selectByUsernameAndEmail(bean);
		if(userkey != null){
			 Random r = new Random();
			 for (int i = 0; i < 3; i++) {
		            char c = codeSequence[r.nextInt(codeSequence.length)];
		            builderCode.append(c);
		        }
			 String code = builderCode.toString();
			 Map<String,String> emailmap =new HashMap<String,String>();
			 List<ParameterBean> list =  pservice.selectAll();
			 Map<String,String> pmap = new HashMap<String,String>();
			 for(ParameterBean p : list)
					pmap.put(p.getName(), p.getValue());
			 emailmap.put("title", "邮件验证");
			 emailmap.put("content", "您在绿茶中药店查询系统的验证码是："+code);
			 emailmap.put("fromemail", pmap.get("email"));
			 emailmap.put("fromname", pmap.get("email"));
			 emailmap.put("frompassword", pmap.get("password"));
			 emailmap.put("fromhost", pmap.get("host"));
			 emailmap.put("fromport", pmap.get("port"));
			 emailmap.put("toemail", email);
			 try{
			 registerservice.SendEmail(emailmap);
			 String md5code = new EncodeUtil().encodeMD5String(code);
			 map.put("state", "1");
			 map.put("code", md5code);
			 map.put("userid", userkey.getId());
			 map.put("msg", "邮箱已发送，请等待");
			 }catch(Exception e){
				 e.printStackTrace();
				 map.put("state", "-1");
				 map.put("msg", "系统错误");
			 }
			 
		}else{
			map.put("state", "0");
			map.put("msg", "您的用户名或邮箱名输入错误，请查证后再输入");
		}
		return map;
	}
	
	@PostMapping("/hospital/editpassword")
	public @ResponseBody Map<String,Object> editPassword(HttpServletRequest request,
            HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		@SuppressWarnings("static-access")
		String newpassword = new EncodeUtil().encodeBase64String(password);
		UserBean bean = new UserBean();
		bean.setId(Integer.parseInt(userid));
		bean.setPassword(newpassword);
		int r = service.update(bean);
			if(r>0){
				map.put("state", "1");
				map.put("msg", "修改密码成功");
				Cookie cookie = new Cookie("userId", userid);
				//设置为负值的话，则为浏览器进程Cookie(内存中保存)，关闭浏览器就失效。
				cookie.setMaxAge(-1);
				response.addCookie(cookie);
			}else{
				map.put("state", "0");
				map.put("msg", "修改密码失败");
			}
		return map;
	}
	
	@SuppressWarnings("static-access")
	@PostMapping("/hospital/addUser")
	public @ResponseBody Map<String,Object> addUser(HttpServletRequest request,
            HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String passkey = new EncodeUtil().encodeBase64String(password);
		int id = -1;
		UserBean bean1 = new UserBean();
		bean1.setUsername(username);
		int count1 = service.selectCountByUsername(bean1);
		if(count1 > 0){
			map.put("msg", "该用户名已存在，请重新输入");
			map.put("state", "0");
			return map;
		}
		UserBean bean2 = new UserBean();
		bean2.setEmail(email);
		int count2 = service.selectCountByEmail(bean2);
		if(count2 > 0){
			map.put("msg", "该邮箱名已存在，请重新输入");
			map.put("state", "0");
			return map;
		}
		UserBean bean3 = new UserBean();
		bean3.setUsername(username);
		bean3.setEmail(email);
		bean3.setPassword(passkey);
		bean3.setIsonline(0);
		try{
			service.insert(bean3);
			map.put("msg", "注册成功");
			map.put("state", "1");
			UserBean userkey= service.select(bean3);
			if(userkey != null)
				id = userkey.getId();
			map.put("id", id);
			Cookie cookie = new Cookie("userId", id+"");
			//设置为负值的话，则为浏览器进程Cookie(内存中保存)，关闭浏览器就失效。
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
		}catch(Exception e){
			map.put("msg", "注册失败");
			map.put("state", "-1");
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings("static-access")
	@PostMapping("/hospital/deleteUser")
	public @ResponseBody Map<String,Object> deleteUser(HttpServletRequest request,
            HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passkey = new EncodeUtil().encodeBase64String(password);
		UserBean bean = new UserBean();
		bean.setUsername(username);
		bean.setPassword(passkey);
		bean.setIsonline(0);
		try{
			service.deleteByUsernameAndPassword(bean);
			UserBean key = service.select(bean);
			if(key != null){
				map.put("state", "0");
				map.put("msg", "用户删除失败");
				return map;
			}
			map.put("state", "1");
			map.put("msg", "用户删除成功");
		}catch(Exception e){
			map.put("state", "-1");
			map.put("msg", "用户删除失败");
			e.printStackTrace();
		}
		return map;
	}
	
	@PostMapping("/hospital/feedback")
	public @ResponseBody Map<String,Object> feedback(String content,HttpServletRequest request,
            HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
		String s = feedservice.getAnswer(content);
		sb.append("<p  id='blue'>客服回答：");
		sb.append("</p><p id='bluecontent'>");
		sb.append(s);
		sb.append("</p>");
		map.put("msg", sb.toString());
		return map;
	}
	
}

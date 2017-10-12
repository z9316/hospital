package com.example.demo.parameter.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.parameter.domain.ParameterBean;
import com.example.demo.parameter.service.HerbParameterService;

@Controller
@CrossOrigin
public class HerbParameterController {
	
	@Autowired
	private HerbParameterService pservice;
	
	@PostMapping("/herb/parameterview")
	public @ResponseBody Map<String, String> parameterView(HttpServletRequest request,
            HttpServletResponse response){
		Map<String,String> map = new HashMap<String,String>();
		List<ParameterBean> list =  pservice.selectAll();
		for(ParameterBean p : list)
			map.put(p.getName(), p.getValue());
		return map;
	}
	
	@PostMapping("/herb/parameteredit")
	public @ResponseBody Map<String, Object> parameterEdit(HttpServletRequest request,
            HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		String text = request.getParameter("text");
		String scan = request.getParameter("scan");
		String host = request.getParameter("host");
		String port = request.getParameter("port");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		try{
		ParameterBean bean = new ParameterBean();
		bean.setName("luencetext");
		bean.setValue(text);
		int r1 = pservice.update(bean);
		bean = new ParameterBean();
		bean.setName("luencescan");
		bean.setValue(scan);
		int r2 = pservice.update(bean);
		bean = new ParameterBean();
		bean.setName("host");
		bean.setValue(host);
		int r3 = pservice.update(bean);
		bean = new ParameterBean();
		bean.setName("port");
		bean.setValue(port);
		int r4 = pservice.update(bean);
		bean = new ParameterBean();
		bean.setName("name");
		bean.setValue(name);
		int r5 = pservice.update(bean);
		bean = new ParameterBean();
		bean.setName("email");
		bean.setValue(email);
		int r6 = pservice.update(bean);
		bean = new ParameterBean();
		bean.setName("password");
		bean.setValue(password);
		int r7 = pservice.update(bean);
		if(r1 > 0 && r2> 0 && r3> 0 && r4> 0 && r5> 0 && r6> 0 && r7> 0){
			map.put("state", "1");
			map.put("msg","修改成功");
		}else{
			map.put("state", "0");
			map.put("msg","修改失败");
		}
		}catch(Exception e){
			map.put("state", "-1");
			map.put("msg","程序异常");
			e.printStackTrace();
		}
		return map;
	}

}

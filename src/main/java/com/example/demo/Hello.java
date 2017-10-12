package com.example.demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
public class Hello {
	
	@GetMapping("/hospital/getYZM")
	public String getYZM(HttpServletRequest request,
            HttpServletResponse response){
	    int width = 80;//验证码宽度
	    int height = 30;//验证码高度
	    int codeCount = 4;//验证码个数
	    int lineCount = 19;//混淆线个数
	    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
	            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		//定义随机数类
        Random r = new Random();
        //定义存储验证码的类
        StringBuilder builderCode = new StringBuilder();
        //定义画布
        BufferedImage buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //得到画笔
        Graphics g = buffImg.getGraphics();
        //1.设置颜色,画边框
        g.setColor(Color.black);
        g.drawRect(0,0,width,height);
        //2.设置颜色,填充内部
        g.setColor(Color.white);
        g.fillRect(1,1,width-2,height-2);
        //3.设置干扰线
        g.setColor(Color.gray);
        for (int i = 0; i < lineCount; i++) {
            g.drawLine(r.nextInt(width),r.nextInt(width),r.nextInt(width),r.nextInt(width));
        }
        //4.设置验证码
        g.setColor(Color.blue);
        //4.1设置验证码字体
        g.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
        for (int i = 0; i < codeCount; i++) {
            char c = codeSequence[r.nextInt(codeSequence.length)];
            builderCode.append(c);
            g.drawString(c+"",15*(i+1),15);
        }
        ///6.保存到session中
        HttpSession session = request.getSession();
        session.setAttribute("codeValidate",builderCode.toString());
        //5.输出到屏幕
        ServletOutputStream sos = null;
        response.reset();
      //7.禁止图像缓存。
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        response.setContentType("image/png");
		try {
			sos = response.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			ImageIO.write(buffImg,"png",sos);
			
	        //8.关闭sos
			sos.flush();
	        sos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return "login/index";
	}

	@GetMapping("/hospital/login")
	public ModelAndView get(HashMap<String, Long> map){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		ModelAndView  model = new ModelAndView("login/index");
		model.addObject("time", new Date().getTime());
		HttpSession session = request.getSession();
		session.setAttribute("userId", "");
//		Cookie cookie = new Cookie("userId", "");
//		//设置为负值的话，则为浏览器进程Cookie(内存中保存)，关闭浏览器就失效。
//		cookie.setMaxAge(0);
//		response.addCookie(cookie);
		//((Map<String, Long>) model).put("time", new Date().getTime());
		return model;
	}
	
	@GetMapping("/hospital/register")
	public ModelAndView get0(HashMap<String, Long> map){
		ModelAndView  model = new ModelAndView("login/register");
		model.addObject("time", new Date().getTime());
		//((Map<String, Long>) model).put("time", new Date().getTime());
		return model;
	}
	
	@GetMapping("/hospital/aboutus")
	public ModelAndView get2(HashMap<String, Long> map){
		ModelAndView  model = new ModelAndView("login/aboutus");
		//model.addObject("time", new Date().getTime());
		//((Map<String, Long>) model).put("time", new Date().getTime());
		return model;
	}
	
	@GetMapping("/hospital/feedback")
	public ModelAndView get3(HashMap<String, Long> map){
		ModelAndView  model = new ModelAndView("login/feedback");
		//model.addObject("time", new Date().getTime());
		//((Map<String, Long>) model).put("time", new Date().getTime());
		return model;
	}
	
	@GetMapping("/hospital/error404")
	public ModelAndView Error404(){
		ModelAndView  model = new ModelAndView("error/404");
		return model;
	}
	
}

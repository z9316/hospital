package com.example.demo.conf;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HerbLoginInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
	//	Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		String value = "";
		/*for(Cookie cookie : cookies){
		   String name = cookie.getName();// get the cookie name
		   if("userId".equals(name)){
			   value = cookie.getValue(); // get the cookie value
			   break;
		   }
		}*/
		HttpSession session = request.getSession();
		value = (String) session.getAttribute("userId");
		if(value == null || "".equals(value)){
			response.sendRedirect(request.getContextPath()+"/hospital/error404");
			return false;
		}
		return true;
	}

}

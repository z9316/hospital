package com.example.demo.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
public class HerbHomeController {

	@GetMapping("/herb/home")
	public ModelAndView HerbHome(){
		ModelAndView  model = new ModelAndView("home/home");
	//	ModelAndView  model = new ModelAndView("home/search");
		return model;
	}
	
	@GetMapping("/herb/add")
	public ModelAndView HerbAdd(){
		ModelAndView  model = new ModelAndView("home/add");
		return model;
	}
	
	@GetMapping("/herb/search")
	public ModelAndView HerbSearch(){
		ModelAndView  model = new ModelAndView("home/search");
		return model;
	}
	
	@GetMapping("/herb/parameter")
	public ModelAndView HerbParameter(){
		ModelAndView  model = new ModelAndView("home/parameter");
		return model;
	}
	
}

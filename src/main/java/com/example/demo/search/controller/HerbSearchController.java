package com.example.demo.search.controller;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.search.service.HerbSearchService;


@Controller
@CrossOrigin
public class HerbSearchController {
	
//	public static final String INDEX_PATH = "F:/lucene/"; // 存放Lucene索引文件的位置
//	public static final String SCAN_PATH = "F:/text/"; // 需要被扫描的位置，测试的时候记得多在这下面放一些文件
	
	@Autowired
	private HerbSearchService searchservice;
	
	@PostMapping("/herb/searchcontent")
	public @ResponseBody Map<String,Object> getSearch(HttpServletRequest request,
            HttpServletResponse response){
		String newcontent = request.getParameter("newcontent");
		String pagenumber = request.getParameter("pagenumber");
		String content = "";
		try {
		//	content = new EncodeUtil().decodeBase64String(newcontent);
			content = newcontent;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(content);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("content", content);
		map.put("pagenumber", pagenumber);
		Map<String,Object> qmap = searchservice.searchByQuery(map);
		return qmap;
	}

	@PostMapping("/herb/createindex")
	public @ResponseBody Map<String,Object> createIndex(HttpServletRequest request,
            HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		searchservice.createIndex();
		return map;
	}
}

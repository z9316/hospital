package com.example.demo.add.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.demo.parameter.domain.ParameterBean;
import com.example.demo.parameter.service.HerbParameterService;


import com.example.demo.tool.FileUtil;

@Controller
@CrossOrigin
public class HerbAddController {

	@Autowired
	private HerbParameterService pservice;
	
//	public static final String INDEX_PATH = "F:/lucene/"; // 存放Lucene索引文件的位置
//	public static final String SCAN_PATH = "F:/text/"; // 需要被扫描的位置，测试的时候记得多在这下面放一些文件
	
	
	@PostMapping("/herb/writeadd")
	public @ResponseBody Map<String,Object> HerbWriteAdd(HttpServletRequest request,
            HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,String> pmap = new HashMap<String,String>();
		List<ParameterBean> list =  pservice.selectAll();
		for(ParameterBean p : list)
			pmap.put(p.getName(), p.getValue());
	//	String INDEX_PATH = pmap.get("luencescan");
		String SCAN_PATH = pmap.get("luencetext");
		
		String name = request.getParameter("name");
		String text = request.getParameter("text");
		String textname = SCAN_PATH + name +".txt";
		FileWriter writer;
		File file = new File(textname);
		try {
		if(!file.exists()){
			file.createNewFile();
			writer = new FileWriter(textname);
			byte[] b = text.getBytes();
			writer.write(new String(b,"UTF-8"));
			writer.flush();
			writer.close();

			map.put("msg", "操作成功");
			map.put("state", "1");
		}else{
			map.put("msg", "该文件名已存在，请重新输入");
			map.put("state", "0");
			return map;
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("state", "-1");
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	@PostMapping("/herb/uploadadd")
	public @ResponseBody Map<String,Object> HerUploadAdd(HttpServletRequest request,
            HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,String> pmap = new HashMap<String,String>();
		List<ParameterBean> list =  pservice.selectAll();
		for(ParameterBean p : list)
			pmap.put(p.getName(), p.getValue());
	//	String INDEX_PATH = pmap.get("luencescan");
		String SCAN_PATH = pmap.get("luencetext");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		MultipartFile multipartFile = multipartRequest.getFile("file"); 
		String ofilename = multipartFile.getOriginalFilename();
		int index = ofilename.lastIndexOf(".");
		String filename = ofilename.substring(0, index);
		String suffix = ofilename.substring(index+1);
		if(!("txt".equalsIgnoreCase(suffix)||"doc".equalsIgnoreCase(suffix)||"docx".equalsIgnoreCase(suffix))){
			map.put("msg", "该文件类型不正确，请重新上传");
			map.put("state", "0");
			return map;
		}
		String textname = SCAN_PATH + filename +".txt";
		String textname0 = SCAN_PATH + ofilename;
		File file = new File(textname);
		File file0 = new File(textname0);
		InputStream in = null;
		FileWriter writer = null;
		String content = "";
		try {
		if(!file.exists()){
			file.createNewFile();
			file0.createNewFile();
			
			DiskFileItemFactory factory = new DiskFileItemFactory();  
	        //2、创建一个文件上传解析器  
	        ServletFileUpload upload = new ServletFileUpload(factory);  
	        //解决上传文件名的中文乱码  
	        upload.setHeaderEncoding("UTF-8");   
	        byte[] buffer = new byte[1024];  
            int len = 0;  
            in = multipartFile.getInputStream();
            OutputStream out = new FileOutputStream(textname0); 
            while ((len = in.read(buffer)) != -1) {  
                out.write(buffer, 0, len);  
            }  
           
            if("txt".equalsIgnoreCase(suffix)){
            	content = FileUtil.readFile(textname0,true);
            }else if("doc".equalsIgnoreCase(suffix)){
            	FileInputStream fis = new FileInputStream(file0);
            	HWPFDocument doc = new HWPFDocument(fis);
                StringBuilder sb = doc.getText();
                content = sb.toString();
            }else if("docx".equalsIgnoreCase(suffix)){
            	FileInputStream fis = new FileInputStream(file0);
            	XWPFDocument doc = new XWPFDocument(fis);  
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc); 
                content = extractor.getText();
            }
         //   String[] strs = content.split("\\n");
         //   StringBuffer sb = new StringBuffer();
         //   for(String s : strs){
         //   	sb.append("<p style='text-indent: 2em;'>"+s+"</p>");
         //  }
            out.close();  
            in.close(); 
            file0.delete();
            writer = new FileWriter(textname);
		//	writer.write(sb.toString());
            byte[] b = content.getBytes();
			writer.write(new String(b));
			writer.flush();
			writer.close();
             
     
            map.put("msg", "操作成功");
			map.put("state", "1");
		}else{
			map.put("msg", "该文件名已存在，请重新上传");
			map.put("state", "0");
			return map;
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("state", "-1");
			map.put("msg", "操作失败");
		}finally{
			try {
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("state", "-1");
				map.put("msg", "操作失败");
			}
		}
		return map;
	}

}

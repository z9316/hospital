package com.example.demo.login.service;

import java.io.*;
import java.util.*;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class FeedServiceImpl implements FeedbackService{

	@Override
	public String getAnswer(String content) {
		// TODO Auto-generated method stub
		String answer = "";
		 //创建SAXReader对象  
        SAXReader reader = new SAXReader();  
        //读取文件 转换成Document  
        Document document;
		try {
			File file =  ResourceUtils.getFile("classpath:xml/robothd.xml");
			document = reader.read(file);
        //获取根节点元素对象  
        Element root = document.getRootElement(); 
        Map<String,String> qmap = this.getSelectedNode("question", root);
        Set<String> set = qmap.keySet();
        double max = 0;
        String maxindex = "0";
        String maxstr = "对不起，我们找不到您想要的答案";
        for(String str : set){
        	String biaozhun = qmap.get(str);
        	double per = this.getXiangsiQuestion(biaozhun,content);
        	if(per > max && per >= 0.5){
        		max = per;
        		maxstr = biaozhun;
        		maxindex = str;
        	}
        }
        if(max != 0){
        	 Map<String,String> amap = this.getSelectedNode("answer", root);
        	 answer = amap.get(maxindex);
        }else{
        	answer = maxstr;
        }
     //   this.listNodes(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			answer = "您的提问非法";
		}  
		return answer;
	}
	
	 //遍历当前节点下的所有节点  
    @SuppressWarnings({ "unchecked", "unused" })
	public void listNodes(Element node){  
     //   System.out.println("当前节点的名称：" + node.getName());  
        //首先获取当前节点的所有属性节点  
        List<Attribute> list = node.attributes();  
        //遍历属性节点  
     //   for(Attribute attribute : list){  
     //       System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  
     //   }  
        //如果当前节点内容不为空，则输出  
     //   if(!(node.getTextTrim().equals(""))){  
    //         System.out.println( node.getName() + "：" + node.getText());    
     //   }  
        //同时迭代当前节点下面的所有子节点  
        //使用递归  
        Iterator<Element> iterator = node.elementIterator();  
        while(iterator.hasNext()){  
            Element e = iterator.next();  
            listNodes(e);  
        }  
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,String> getSelectedNode(String name,Element node){
    	Map<String,String> map = new HashMap<String,String>();
    	try{
    		 Element element = node.element(name);
    		 List<Element> list = element.elements();
    		for (int i=0;i<list.size();i++) {
                map.put(list.get(i).attributeValue("name")+"", list.get(i).getTextTrim()+"");
     
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return map;
    }
    
    public double getXiangsiQuestion(String biaozhun,String ceshi){
    	 String newStrA = removeSign(biaozhun);  
         String newStrB = removeSign(ceshi);  
         int temp = Math.max(newStrA.length(), newStrB.length());  
         int temp2 = longestCommonSubstring(newStrA, newStrB).length();  
         double per =  temp2 * 1.0 / temp; 
    	 return per;
    }
    
    private static String removeSign(String str) {  
        StringBuffer sb = new StringBuffer();  
        for (char item : str.toCharArray())  
            if (charReg(item)){  
                //System.out.println("--"+item);  
                  sb.append(item);  
              }  
        return sb.toString();  
    }  
  
  
  
    private static boolean charReg(char charValue) {  
  
        return (charValue >= 0x4E00 && charValue <= 0X9FA5)  
                || (charValue >= 'a' && charValue <= 'z')  
                || (charValue >= 'A' && charValue <= 'Z')  
                || (charValue >= '0' && charValue <= '9');  
    }  
    

	private static String longestCommonSubstring(String strA, String strB) {  
        char[] chars_strA = strA.toCharArray();  
        char[] chars_strB = strB.toCharArray();  
        int m = chars_strA.length;  
        int n = chars_strB.length;  
        int[][] matrix = new int[m + 1][n + 1];  
        for (int i = 1; i <= m; i++) {  
              for (int j = 1; j <= n; j++) {  
                if (chars_strA[i - 1] == chars_strB[j - 1])  
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;  
                else  
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);  
              }  
        }  
        char[] result = new char[matrix[m][n]];  
        int currentIndex = result.length - 1;  
        while (matrix[m][n] != 0) {  
            if (matrix[n] == matrix[n - 1])  
                n--;  
            else if (matrix[m][n] == matrix[m - 1][n])   
                m--;  
            else {  
                result[currentIndex] = chars_strA[m - 1];  
                currentIndex--;  
                n--;  
                m--;  
            }  
        }  
        return new String(result);  
    }  

}

package com.example.demo.tool;

import java.io.IOException;
import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder; 

@SuppressWarnings("restriction")
public class EncodeUtil {
	

	/** 
     * 用MD5算法进行加密 
     * @param str 需要加密的字符串 
     * @return MD5加密后的结果 
     */  
    public static String encodeMD5String(String str) {  
        return encode(str, "MD5");  
    }  
  
    /** 
     * 用SHA算法进行加密 
     * @param str 需要加密的字符串 
     * @return SHA加密后的结果 
     */  
    public static String encodeSHAString(String str) {  
        return encode(str, "SHA");  
    }  
  
    /** 
     * 用base64算法进行加密 
     * @param str 需要加密的字符串 
     * @return base64加密后的结果 
     */  
    public static String encodeBase64String(String str) {  
        BASE64Encoder encoder =  new BASE64Encoder();  
        return encoder.encode(str.getBytes());  
    }  
      
    /** 
     * 用base64算法进行解密 
     * @param str 需要解密的字符串 
     * @return base64解密后的结果 
     * @throws IOException  
     */  
    public static String decodeBase64String(String str) throws IOException {  
        BASE64Decoder encoder =  new BASE64Decoder();  
        return new String(encoder.decodeBuffer(str),"utf-8");  
    }  
      
    private static String encode(String str, String method) {  
        MessageDigest md = null;  
        String dstr = null;  
        try {  
            md = MessageDigest.getInstance(method);  
            md.update(str.getBytes());  
            dstr = new BigInteger(1, md.digest()).toString(16);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return dstr;  
    }  

      
    public static void main(String[] args) throws IOException {  
        String user = "我是人";  
        System.out.println("原始字符串 " + user);  
        System.out.println("MD5加密 " + encodeMD5String(user));  
        System.out.println("SHA加密 " + encodeSHAString(user));  
        String base64Str = encodeBase64String(user);  
        System.out.println("Base64加密 " + base64Str);  
        System.out.println("Base64解密 " + decodeBase64String(base64Str));  
    }  
	
}

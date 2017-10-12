package com.example.demo.search.domain;

/**
 * @author lenovo
 *
 */
public class QueryBean {
	
	/***
	 * 文件名称
	 */
	private String filename;
	/***
	 * 文件路径
	 */
	private String filepath;
	/***
	 * 文件内容
	 */
	private String content;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}

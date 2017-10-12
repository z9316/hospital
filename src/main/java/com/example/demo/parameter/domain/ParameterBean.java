package com.example.demo.parameter.domain;

public class ParameterBean {
	
	/***
	 * 主键
	 */
	private int id;
	/***
	 * 参数名称
	 */
	private String name;
	/***
	 * 参数值
	 */
	private String value;
	/***
	 * 参数描述
	 */
	private String des;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}

}

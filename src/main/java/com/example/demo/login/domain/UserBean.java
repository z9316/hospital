package com.example.demo.login.domain;


/**
 * @author lenovo
 *
 */
public class UserBean {

	/**
	 * 用户序号
	 */
	private int id;
	/**
	 * 用户名字
	 */
	private String username;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 用户是否在线1=在线，0=离线，-1=其他
	 */
	private int isonline;
	/**
	 * 用户邮箱
	 */
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsonline() {
		return isonline;
	}

	public void setIsonline(int isonline) {
		this.isonline = isonline;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

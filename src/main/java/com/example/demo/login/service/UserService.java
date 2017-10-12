package com.example.demo.login.service;

import java.util.List;

import com.example.demo.login.domain.UserBean;


public interface UserService {

    public void insert(UserBean bean);
	
	public void delete(UserBean bean);
	
	public void deleteByUsernameAndPassword(UserBean bean);
	
	public int update(UserBean bean);
	
	public List<UserBean> selectAll();
	
	public UserBean select(UserBean cause);
	
	public UserBean selectById(UserBean cause);
	
	public UserBean selectByUsernameAndEmail(UserBean cause);
	
	public int selectCountByUsername(UserBean cause);
	
	public int selectCountByEmail(UserBean cause);
	
}

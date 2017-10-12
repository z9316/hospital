package com.example.demo.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.login.domain.UserBean;
import com.example.demo.login.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	//@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void insert(UserBean bean) {
		// TODO Auto-generated method stub
		userMapper.insert(bean);
	}

	@Override
	//@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void delete(UserBean bean) {
		// TODO Auto-generated method stub
		userMapper.delete(bean);
	}
	
	@Override
	//@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void deleteByUsernameAndPassword(UserBean bean) {
		// TODO Auto-generated method stub
		userMapper.deleteByUsernameAndPassword(bean);
	}

	@Override
	//@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public int update(UserBean bean) {
		// TODO Auto-generated method stub
		return userMapper.update(bean);
	//	return 0;
	}

	@Override
	public List<UserBean> selectAll() {
		// TODO Auto-generated method stub
		return userMapper.selectAll();
	}

	@Override
	public UserBean select(UserBean bean) {
		// TODO Auto-generated method stub
		return userMapper.select(bean);
	//	return null;
	}
	
	@Override
	public UserBean selectById(UserBean cause) {
		// TODO Auto-generated method stub
		return userMapper.selectById(cause);
	}
	

	@Override
	public UserBean selectByUsernameAndEmail(UserBean cause) {
		// TODO Auto-generated method stub
		return userMapper.selectbyUserNameAndEmail(cause);
	}

	@Override
	public int selectCountByUsername(UserBean cause) {
		// TODO Auto-generated method stub
		return userMapper.selectCountByUsername(cause);
	}

	@Override
	public int selectCountByEmail(UserBean cause) {
		// TODO Auto-generated method stub
		return userMapper.selectCountByEmail(cause);
	}

	

	

}

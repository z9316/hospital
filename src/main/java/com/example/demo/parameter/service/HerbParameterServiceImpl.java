package com.example.demo.parameter.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.parameter.domain.ParameterBean;
import com.example.demo.parameter.mapper.ParameterMapper;

@Service
public class HerbParameterServiceImpl implements HerbParameterService{
	
	@Autowired
	private ParameterMapper parametermapper;

	@Override
	public int update(ParameterBean bean) {
		// TODO Auto-generated method stub
		return parametermapper.update(bean);
	}

	@Override
	public ParameterBean selectByName(ParameterBean bean) {
		// TODO Auto-generated method stub
		return parametermapper.selectByName(bean);
	}

	@Override
	public List<ParameterBean> selectAll() {
		// TODO Auto-generated method stub
		return parametermapper.selectAll();
	}
	
}

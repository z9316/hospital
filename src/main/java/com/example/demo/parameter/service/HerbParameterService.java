package com.example.demo.parameter.service;

import java.util.*;

import com.example.demo.parameter.domain.ParameterBean;

public interface HerbParameterService {

	public ParameterBean selectByName(ParameterBean bean);
	
	public List<ParameterBean> selectAll();
	
	public int update(ParameterBean bean);
}

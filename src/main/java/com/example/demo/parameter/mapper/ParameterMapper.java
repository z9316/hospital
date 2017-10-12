package com.example.demo.parameter.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.parameter.domain.ParameterBean;

public interface ParameterMapper{
	
	@Update("UPDATE parameter SET value=#{value} WHERE name = #{name}")
	public int update(ParameterBean bean);
	
	@Select("SELECT id,name,value,des FROM parameter where name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "value", column = "value"),
		@Result(property = "des", column = "des")
	})
	public ParameterBean selectByName(ParameterBean bean);
	
	@Select("SELECT id,name,value,des FROM parameter")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "value", column = "value"),
		@Result(property = "des", column = "des")
	})
	public List<ParameterBean> selectAll();
	
}

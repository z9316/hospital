package com.example.demo.login.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.login.domain.UserBean;

public interface UserMapper{
	
	@Insert("INSERT INTO man(username,password,email,isonline) values(#{username},#{password},#{email},#{isonline})")
    public void insert(UserBean bean);
	
	@Delete("DELETE FROM man WHERE id = #{id}")
	public void delete(UserBean bean);
	
	@Delete("DELETE FROM man WHERE username = #{username} and password = #{password}")
	public void deleteByUsernameAndPassword(UserBean bean);
	
	@Update("UPDATE man SET password=#{password} WHERE id = #{id}")
	public int update(UserBean bean);
	
	@Select("SELECT id,username,password,email,isonline FROM man")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "username", column = "username"),
		@Result(property = "password", column = "password"),
		@Result(property = "email", column = "email"),
		@Result(property = "isonline", column = "isonline")
	})
	public List<UserBean> selectAll();
	
	@Select("SELECT id,username,password,email,isonline FROM man WHERE username=#{username} and password=#{password} and isonline=#{isonline}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "username", column = "username"),
		@Result(property = "password", column = "password"),
		@Result(property = "email", column = "email"),
		@Result(property = "isonline", column = "isonline")
	})
	public UserBean select(UserBean bean);
	
	@Select("SELECT id,username,password,email,isonline FROM man WHERE id=#{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "username", column = "username"),
		@Result(property = "password", column = "password"),
		@Result(property = "email", column = "email"),
		@Result(property = "isonline", column = "isonline")
	})
	public UserBean selectById(UserBean bean);
	
	@Select("SELECT id,username,password,email,isonline FROM man WHERE username = #{username} and email = #{email}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "username", column = "username"),
		@Result(property = "password", column = "password"),
		@Result(property = "email", column = "email"),
		@Result(property = "isonline", column = "isonline")
	})
	public UserBean selectbyUserNameAndEmail(UserBean bean);
	
	@Select("SELECT count(1) as count FROM man where username = #{username}")
    public int selectCountByUsername(UserBean cause);
	
	@Select("SELECT count(1) as count FROM man where email = #{email}")
	public int selectCountByEmail(UserBean cause);
	
}

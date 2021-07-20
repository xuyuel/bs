package com.sjzc.kt.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {

	//后台管理登录
	Map<String, Object> getSysUser(Map<String, Object> map);
	
	Map<String, Object> getSysUserByNumber(Map<String, Object> map);
	
	//移动端注册
	void register(Map<String, Object> map);
	
	//修改密码
	void updatePassword(Map<String, Object> map);
	
	//修改头像
	void updateHead(Map<String, Object> map);
	
	//添加教师信息
	void addTeacher(Map<String, Object> map);
	
	//用户信息
	Map<String, Object> memberInfo(Integer memberId);
	
}

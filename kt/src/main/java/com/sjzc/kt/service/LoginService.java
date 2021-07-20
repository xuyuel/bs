package com.sjzc.kt.service;

import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjzc.kt.dao.LoginDao;
import com.sjzc.kt.dao.TbstaticMapper;
import com.sjzc.kt.entity.TbstaticExample;
import com.sjzc.kt.exception.RRException;
import com.sjzc.kt.util.Md5Util;
@Service
public class LoginService {
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private TbstaticMapper staticMapper;
	//登录
	public Map<String, Object> consoleLogin(Map<String, Object> sysUser){
		Map<String, Object> sys = sysUser;
		Map<String, Object> user=loginDao.getSysUser(sysUser);
		String strMd5 = Md5Util.strMd5(sysUser.get("password").toString());
		//String strMd5 = DigestUtils.md5Hex(sysUser.get("password").toString());
		if(user==null){
			throw new RRException("账号不正确！");
		}
		else{
			if(user.get("role").toString().equals("1")){
				throw new RRException("您的身份不是管理员!");
			}
			if(!user.get("password").equals(Md5Util.strMd5(sysUser.get("password").toString()))){
				throw new RRException("密码不正确！");
			}
		}
		return user;
	
	}
	//学生注册
	public void register(Map<String, Object> sysUser){
		Map<String, Object> sys = sysUser;
		Map<String, Object> user=loginDao.getSysUser(sysUser);
		
		Map<String, Object> user1=loginDao.getSysUserByNumber(sysUser);
		if (user!=null) {
			throw new RRException("该手机号已被注册");
		}
		if (user1!=null) {
			throw new RRException("该学号已被注册");
		}
		String strMd5 = Md5Util.strMd5(sysUser.get("password").toString());
		//String strMd5 = DigestUtils.md5Hex(sysUser.get("password").toString());
		sysUser.put("strMd5", strMd5);
		sysUser.put("icon", "20210428114331447.jpg");
		
		loginDao.register(sysUser);
	
	}
	//移动端登录
	public Map<String, Object> frontLogin(Map<String, Object> sysUser){
		Map<String, Object> sys = sysUser;
		Map<String, Object> user=loginDao.getSysUser(sysUser);
		String strMd5 = Md5Util.strMd5(sysUser.get("password").toString());
		//String strMd5 = DigestUtils.md5Hex(sysUser.get("password").toString());
		if(user==null){
			throw new RRException("账号不正确！");
		}
		else{
			if(user.get("role").toString().equals("3")){
				throw new RRException("您的身份不是教师或学生!");
			}
			if(!user.get("password").equals(Md5Util.strMd5(sysUser.get("password").toString()))){
				throw new RRException("密码不正确！");
			}
			if (user.get("role").toString().equals("1")) {
				//查询专业年级
				TbstaticExample staticExample1 = new TbstaticExample();
				staticExample1.createCriteria().andIdEqualTo(Integer.valueOf(user.get("department").toString())).andDelStateEqualTo(1);
				user.put("departmentFormat", staticMapper.selectByExample(staticExample1).get(0).getDescription());
				
				TbstaticExample staticExample2 = new TbstaticExample();
				staticExample2.createCriteria().andIdEqualTo(Integer.valueOf(user.get("major").toString())).andDelStateEqualTo(1);
				user.put("majorFormat", staticMapper.selectByExample(staticExample2).get(0).getDescription());
				
				TbstaticExample staticExample3 = new TbstaticExample();
				staticExample3.createCriteria().andIdEqualTo(Integer.valueOf(user.get("grades").toString())).andDelStateEqualTo(1);
				user.put("gradesFormat", staticMapper.selectByExample(staticExample3).get(0).getDescription());
			}
			
		}
		return user;
	
	}
	//修改密码
	public void updatePassword(Map<String, Object> map){
		//Integer memberId = Integer.valueOf(map.get("memberId").toString());
		String oldPassword = map.get("oldPassword").toString();
		
		
		Map<String, Object> user=loginDao.getSysUser(map);
		String oldPasswordMd5 = Md5Util.strMd5(oldPassword);
		
		if (!oldPasswordMd5.equals(user.get("password").toString())) {
			throw new RRException("旧密码不正确");
		}
		//修改密码
		String newPasswordMd5 = Md5Util.strMd5(map.get("newPassword").toString());
		map.put("newPasswordMd5", newPasswordMd5);
		loginDao.updatePassword(map);
	}
	
	//修改头像
	public void updateHead(Map<String, Object> map){
		//Integer memberId = Integer.valueOf(map.get("memberId").toString());
		loginDao.updateHead(map);
	}
	//添加教师信息
	public void addTeacher(Map<String, Object> sysUser){
		Map<String, Object> user=loginDao.getSysUser(sysUser);
		if (user!=null) {
			throw new RRException("该手机号已被注册");
		}
		//默认密码 111111
		String strMd5 = Md5Util.strMd5("111111");
		//String strMd5 = DigestUtils.md5Hex(sysUser.get("password").toString());
		sysUser.put("strMd5", strMd5);
		sysUser.put("icon", "20210428114331447.jpg");
		loginDao.addTeacher(sysUser);
	
	}
	
	// 用户信息
	public Map<String, Object> memberInfo(Integer memberId) {
		//Map<String, Object> sys = sysUser;
		Map<String, Object> user = loginDao.memberInfo(memberId);
		
		// String strMd5 = DigestUtils.md5Hex(sysUser.get("password").toString());
		if (user != null) {
			if (user.get("role").toString().equals("1")) {
				// 查询专业年级
				TbstaticExample staticExample1 = new TbstaticExample();
				staticExample1.createCriteria().andIdEqualTo(Integer.valueOf(user.get("department").toString()))
						.andDelStateEqualTo(1);
				user.put("departmentFormat", staticMapper.selectByExample(staticExample1).get(0).getDescription());

				TbstaticExample staticExample2 = new TbstaticExample();
				staticExample2.createCriteria().andIdEqualTo(Integer.valueOf(user.get("major").toString()))
						.andDelStateEqualTo(1);
				user.put("majorFormat", staticMapper.selectByExample(staticExample2).get(0).getDescription());

				TbstaticExample staticExample3 = new TbstaticExample();
				staticExample3.createCriteria().andIdEqualTo(Integer.valueOf(user.get("grades").toString()))
						.andDelStateEqualTo(1);
				user.put("gradesFormat", staticMapper.selectByExample(staticExample3).get(0).getDescription());
			}
		}
		return user;
	}
}

package com.sjzc.kt.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sjzc.kt.service.LoginService;
import com.sjzc.kt.util.R;

@RestController
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	//后台管理登录
	@PostMapping("/consoleLogin")
	public R consoleLogin(@RequestBody Map<String, Object> sysUser) {
		Object o = sysUser.get("phone");
		Object p = sysUser.get("password");
		 //验证账号密码
		Map<String, Object> result;
		result = loginService.consoleLogin(sysUser);
		
		return R.ok().put("result", result);
	}
	//移动端注册
	@PostMapping("/register")
	public R register(@RequestBody Map<String, Object> user) {
		Object o = user.get("phone");
		Object p = user.get("password");
		loginService.register(user);
		
		return R.ok();
	}

	// 移动端登录
	@PostMapping("/frontLogin")
	public R frontLogin(@RequestBody Map<String, Object> sysUser) {
		Object o = sysUser.get("phone");
		Object p = sysUser.get("password");
		// 验证账号密码
		Map<String, Object> result;
		result = loginService.frontLogin(sysUser);

		return R.ok().put("result", result);
	}

	//修改密码
	@PostMapping("/updatePassword")
	public R updatePassword(@RequestBody Map<String, Object> map) {
		
		loginService.updatePassword(map);
		return R.ok("修改成功");
	}

	//修改头像
	@PostMapping("/updateHead")
	public R updateHead(@RequestBody Map<String, Object> map) {
		
		loginService.updateHead(map);
		return R.ok("修改成功");
	}
	
	//用户信息
	@GetMapping("memberInfo")
	public R memberInfo(@RequestParam Integer memberId) {
		
		Map<String, Object> result = loginService.memberInfo(memberId);
		return R.ok().put("result", result);
	}
}

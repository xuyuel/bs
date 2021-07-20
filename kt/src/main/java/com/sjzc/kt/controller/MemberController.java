package com.sjzc.kt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjzc.kt.dao.ClassRelationMapper;
import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.dao.TestMapper;
import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.ClassRelationExample;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.Test;
import com.sjzc.kt.entity.TestExample;
import com.sjzc.kt.exception.RRException;
import com.sjzc.kt.service.CourseService;
import com.sjzc.kt.service.MemberService;
import com.sjzc.kt.util.R;

@RestController
@RequestMapping("member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberMapper memberMapper;
	
	//用户信息列表
	@PostMapping("/memberList")
	public R memberList(@RequestBody Member member) {
		PageHelper.startPage(member.getPageNum(), member.getPageSize());
		List<Member> memberList = memberService.memberList(member);
		PageInfo<Member> pageInfo = new PageInfo<Member>(memberList);
		return R.ok().put("memberList", pageInfo);
	}
	
	/*//新增用户信息
	public R addMember(Map<String,Object> map) {
		
		Map<String, Object> result=new HashMap<String,Object>();
		String openId=map.get("openid").toString();
		// 查询openid是否存在
		int count = memberMapper.quaryOpenid(openId);
		
		Member member = new Member();
		member.setOpenId(openId);
		member.setWechatIcon(map.get("wechatIcon").toString());
		member.setWechatNickname(map.get("wechatNickname").toString());
		
		
		if (count == 0) {
			//插入用户信息，并得到用户id
			memberMapper.insertMember(member);
			
			result.put("state", 1);
		}else {
			
			Map<String, Object> memberMap = memberMapper.quaryMember(openId);
			
			member.setMemberId(Integer.parseInt(memberMap.get("memberId").toString()));
			//weChatVo.setOpenid(openid);
			//weChatVo.setMemberId(Integer.parseInt(memberMap.get("memberId").toString()));
			//result.put("state", 2);
			}
		//Map<String,Object> tokenMap=memberInfortionService.getWehatToken(weChatVo);

			// 获取双token
			result.put("accessToken", tokenMap.get("accessToken"));
			result.put("refreshToken", tokenMap.get("refreshToken"));
			
			return R.ok().put("data", result);
	}*/
	
	//删除学生
	@GetMapping("/delMember")
	public R delMember(@RequestParam Integer id) {
		
		memberService.delMember(id);
		return R.ok("删除成功");
	}
	@PostMapping("update")
	public R update(@RequestBody Member member) {
		
		memberService.update(member);
		return R.ok("修改成功");
	}
	
	
}

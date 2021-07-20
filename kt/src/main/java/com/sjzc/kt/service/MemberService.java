package com.sjzc.kt.service;

import java.util.List;
import java.util.Map;

import com.sjzc.kt.entity.Member;

/**
 * @ClassName: MemberService
 * @Description: TODO(描述)
 * @author xyl
 * @date 2021-02-20
 */
public interface MemberService {

	/**
	 * @Title: memberList
	 * @Description: 用户列表
	 * @author xyl
	 * @date 2021-02-20
	 */
	public List<Member> memberList(Member member);
	
	/*//教师后台管理-根据绑定的课程id查询学生列表
	public List<Member> memberListByTestId(Map<String, Object> map);*/
	
	//根据用户id查询用户信息
	public Member selectMemberById(Integer memberId);
	
	//根据课程关联id查询学生列表
	public List<Member> selectMemberByCRId(Integer classRelationId);
	
	//删除学生
	public void delMember(Integer id);
	
	public void update(Member member);
}

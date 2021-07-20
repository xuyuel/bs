package com.sjzc.kt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjzc.kt.dao.ClassRelationMapper;
import com.sjzc.kt.dao.CommentMapper;
import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.dao.PostMapper;
import com.sjzc.kt.dao.QuestionResponseMapper;
import com.sjzc.kt.dao.TaskAnswerMapper;
import com.sjzc.kt.dao.TbstaticMapper;
import com.sjzc.kt.dao.TestAnswerMapper;
import com.sjzc.kt.entity.ClassRelationExample;
import com.sjzc.kt.entity.Comment;
import com.sjzc.kt.entity.CommentExample;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.MemberExample;
import com.sjzc.kt.entity.MemberExample.Criteria;
import com.sjzc.kt.entity.Post;
import com.sjzc.kt.entity.PostExample;
import com.sjzc.kt.entity.QuestionResponse;
import com.sjzc.kt.entity.QuestionResponseExample;
import com.sjzc.kt.entity.TaskAnswer;
import com.sjzc.kt.entity.TaskAnswerExample;
import com.sjzc.kt.entity.TbstaticExample;
import com.sjzc.kt.entity.TestAnswer;
import com.sjzc.kt.entity.TestAnswerExample;
import com.sjzc.kt.service.MemberService;
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private TbstaticMapper staticMapper;
	@Autowired
	private ClassRelationMapper classRelationMapper;
	@Autowired
	private TestAnswerMapper testAnswerMapper;
	@Autowired
	private TaskAnswerMapper taskAnswerMapper;
	@Autowired
	private QuestionResponseMapper questionResponseMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private CommentMapper commentMapper;
	/**  
	 * @Title: memberList
	 * @Description: 后台管理用户列表
	 * @author xyl
	 * @date 2021-02-20 
	 */
	@Override
	public List<Member> memberList(Member member) {
		// TODO Auto-generated method stub
		MemberExample memberExample = new MemberExample();
		Criteria criteria = memberExample.createCriteria();
		criteria.andDelStateEqualTo(1).andRoleEqualTo(member.getRole());
		//id条件查询
		if (member.getMemberId()!=null) {
			criteria.andMemberIdEqualTo(member.getMemberId());
		}
		//学院条件查询
		if (member.getDepartment()!=null) {
			criteria.andDepartmentEqualTo(member.getDepartment());
		}
		//专业条件查询
		if (member.getMajor()!=null) {
			criteria.andMajorEqualTo(member.getMajor());
		}
		//班级条件查询
		if (member.getGrades()!=null) {
			criteria.andGradesEqualTo(member.getGrades());
		}
		//姓名条件查询
		if (member.getName()!=null) {
			criteria.andNameLike("%"+member.getName()+"%");
		}
		List<Member> memberList = memberMapper.selectByExample(memberExample);
		//Member m = new Member();
		if (member.getRole()==2) {
			if(memberList.size()>0 && !memberList.isEmpty()) {
				for (Member m:memberList) {
					Map<String, Object> map = new HashMap<String, Object>();
					//格式化时间
					map.put("createTimeFormat", m.getCreateTime().getTime());
					m.setMap(map);
				}
			}
		}
		if (member.getRole()==1) {
			if(memberList.size()>0 && !memberList.isEmpty()) {
				for (Member m:memberList) {
					Map<String, Object> map = new HashMap<String, Object>();
					
					TbstaticExample staticExample1 = new TbstaticExample();
					staticExample1.createCriteria().andIdEqualTo(m.getDepartment()).andDelStateEqualTo(1);
					map.put("department", staticMapper.selectByExample(staticExample1).get(0).getDescription());
					
					TbstaticExample staticExample2 = new TbstaticExample();
					staticExample2.createCriteria().andIdEqualTo(m.getMajor()).andDelStateEqualTo(1);
					map.put("majormajor", staticMapper.selectByExample(staticExample2).get(0).getDescription());
					
					TbstaticExample staticExample3 = new TbstaticExample();
					staticExample3.createCriteria().andIdEqualTo(m.getGrades()).andDelStateEqualTo(1);
					map.put("grades", staticMapper.selectByExample(staticExample3).get(0).getDescription());
					
					//格式化时间
					map.put("createTimeFormat", m.getCreateTime().getTime());
					m.setMap(map);
				}
				//m = memberList.get(0);
				
			}
		}
		
		return memberList;
	}
	/*@Override
	public List<Member> memberListByTestId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		return null;
	}*/
	//根据用户id查询用户信息
	@Override
	public Member selectMemberById(Integer memberId) {
		// TODO Auto-generated method stub
		MemberExample memberExample = new MemberExample();
		memberExample.createCriteria().andMemberIdEqualTo(memberId);
		Member member = memberMapper.selectByExample(memberExample).get(0);
		//如果是学生，查询专业信息
		if (member.getRole()==1) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			TbstaticExample staticExample1 = new TbstaticExample();
			staticExample1.createCriteria().andIdEqualTo(member.getDepartment()).andDelStateEqualTo(1);
			map.put("department", staticMapper.selectByExample(staticExample1).get(0).getDescription());
			
			TbstaticExample staticExample2 = new TbstaticExample();
			staticExample2.createCriteria().andIdEqualTo(member.getMajor()).andDelStateEqualTo(1);
			map.put("majormajor", staticMapper.selectByExample(staticExample2).get(0).getDescription());
			
			TbstaticExample staticExample3 = new TbstaticExample();
			staticExample3.createCriteria().andIdEqualTo(member.getGrades()).andDelStateEqualTo(1);
			map.put("grades", staticMapper.selectByExample(staticExample3).get(0).getDescription());
			member.setMap(map);
		}
		return member;
	}
	//根据课程关联id查询学生列表
	@Override
	public List<Member> selectMemberByCRId(Integer classRelationId) {
		// TODO Auto-generated method stub
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria().andIdEqualTo(classRelationId);
		Integer grades = classRelationMapper.selectByExample(classRelationExample).get(0).getGrades();
		//根据班级查询人员列表
		MemberExample memberExample = new MemberExample();
		memberExample.createCriteria().andGradesEqualTo(grades);
		List<Member> memberList = memberMapper.selectByExample(memberExample);
		return memberList;
	}
	//删除学生
	@Override
	public void delMember(Integer id) {
		// TODO Auto-generated method stub
		//删除课堂测验
		TestAnswer testAnswer = new TestAnswer();
		testAnswer.setDelState(2);
		TestAnswerExample testAnswerExample = new TestAnswerExample();
		testAnswerExample.createCriteria().andMemberIdEqualTo(id);
		testAnswerMapper.updateByExampleSelective(testAnswer, testAnswerExample);
		//删除课后作业
		TaskAnswer taskAnswer = new TaskAnswer();
		taskAnswer.setDelState(2);
		TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
		taskAnswerExample.createCriteria().andMemberIdEqualTo(id);
		taskAnswerMapper.updateByExampleSelective(taskAnswer, taskAnswerExample);
		//删除举手
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setDelState(2);
		QuestionResponseExample questionResponseExample = new QuestionResponseExample();
		questionResponseExample.createCriteria().andMemberIdEqualTo(id);
		questionResponseMapper.updateByExampleSelective(questionResponse, questionResponseExample);
		//删除评论
		Comment comment = new Comment();
		comment.setDelState(2);
		CommentExample commentExample = new CommentExample();
		commentExample.createCriteria().andMemberIdEqualTo(id);
		commentMapper.updateByExampleSelective(comment, commentExample);
		
		//删除帖子
		Post post = new Post();
		post.setDelState(2);
		PostExample postExample = new PostExample();
		postExample.createCriteria().andMemberIdEqualTo(id);
		postMapper.updateByExampleSelective(post, postExample);
		
		//删除学生
		Member member = new Member();
		member.setDelState(2);
		MemberExample memberExample = new MemberExample();
		memberExample.createCriteria().andMemberIdEqualTo(id);
		memberMapper.updateByExampleSelective(member, memberExample);
		
	}
	@Override
	public void update(Member member) {
		// TODO Auto-generated method stub
		MemberExample memberExample = new MemberExample();
		memberExample.createCriteria().andMemberIdEqualTo(member.getMemberId());
		memberMapper.updateByExampleSelective(member, memberExample);
	}

	
	
}

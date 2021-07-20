package com.sjzc.kt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjzc.kt.dao.ClassRelationMapper;
import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.dao.QuestionMapper;
import com.sjzc.kt.dao.QuestionResponseMapper;
import com.sjzc.kt.entity.ClassRelationExample;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.MemberExample;
import com.sjzc.kt.entity.Question;
import com.sjzc.kt.entity.QuestionExample;
import com.sjzc.kt.entity.QuestionResponse;
import com.sjzc.kt.entity.QuestionResponseExample;
import com.sjzc.kt.entity.Test;
import com.sjzc.kt.entity.TestExample;
import com.sjzc.kt.exception.RRException;
import com.sjzc.kt.service.MemberService;
import com.sjzc.kt.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionResponseMapper questionResponseMapper;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ClassRelationMapper classRelationMapper;
	@Autowired
	private MemberMapper memberMapper;

	// 教师移动端发起提问
	@Override
	public void addQuestion(Question question) {
		// TODO Auto-generated method stub
		// 提问状态 待回答
		question.setState(1);
		question.setDelState(1);
		question.setCreateTime(new Date());
		questionMapper.insertSelective(question);

	}

	// 学生移动端端查询提问列表
	@Override
	public List<Question> studentQuestionList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		// 查询待举手问题列表
		Integer classRelationId = Integer.valueOf(map.get("classRelationId").toString());
		QuestionExample questionExample = new QuestionExample();
		questionExample.createCriteria().andClassRelationIdEqualTo(classRelationId).andDelStateEqualTo(1)
				.andStateEqualTo(1);
		List<Question> questionList = questionMapper.selectByExample(questionExample);
		if (questionList.size() > 0 && !questionList.isEmpty()) {
			for (Question q : questionList) {
				if (map.containsKey("memberId")) {
					Integer memberId = Integer.valueOf(map.get("memberId").toString());
					// 查询该用户是否已经举手
					QuestionResponseExample questionResponseExample = new QuestionResponseExample();
					questionResponseExample.createCriteria().andQuestionIdEqualTo(q.getQuestionId())
							.andMemberIdEqualTo(memberId).andDelStateEqualTo(1);
					Map<String, Object> map1 = new HashMap<String, Object>();
					if (questionResponseMapper.countByExample(questionResponseExample) != 0) {
						// 1已举手
						map1.put("userState", 1);
						q.setMap(map1);
					} else {
						// 2未举手
						map1.put("userState", 2);
						q.setMap(map1);
					}
				}
			}
		}

		return questionList;
	}

	// 教师端关闭举手
	@Override
	public void closeQuestion(Integer questionId) {
		// TODO Auto-generated method stub
		QuestionExample questionExample = new QuestionExample();
		questionExample.createCriteria().andQuestionIdEqualTo(questionId);

		Question question = new Question();
		question.setState(2);
		questionMapper.updateByExampleSelective(question, questionExample);

	}

	// 教师端查看举手学生列表
	@Override
	public List<QuestionResponse> handMemberList(Map<String, Object> map) {
		// TODO Auto-generated method stub

		// 查询举手用户列表
		Integer questionId = Integer.valueOf(map.get("questionId").toString());

		QuestionResponseExample questionResponseExample = new QuestionResponseExample();
		questionResponseExample.setOrderByClause("create_time asc");

		questionResponseExample.createCriteria().andQuestionIdEqualTo(questionId).andDelStateEqualTo(1);
		List<QuestionResponse> questionResponseList = questionResponseMapper.selectByExample(questionResponseExample);
		// 查询举手用户信息
		if (questionResponseList.size() > 0 && !questionResponseList.isEmpty()) {
			for (QuestionResponse q : questionResponseList) {
				Member member = memberService.selectMemberById(q.getMemberId());
				Map<String, Object> map1 = new HashMap<>();
				map1.put("number", member.getNumber());
				map1.put("name", member.getName());
				map1.put("createTimeFormat", q.getCreateTime().getTime());
				q.setMap(map1);
			}
		}
		return questionResponseList;
	}

	// 学生端举手
	@Override
	public void hand(Integer questionId, Integer memberId) {
		// TODO Auto-generated method stub

		// 查询该用户是否已经举手
		QuestionResponseExample questionResponseExample = new QuestionResponseExample();
		questionResponseExample.createCriteria().andQuestionIdEqualTo(questionId).andMemberIdEqualTo(memberId)
				.andDelStateEqualTo(1);
		if (questionResponseMapper.countByExample(questionResponseExample) != 0) {
			throw new RRException("您已经举手");
		}
		QuestionResponse questionResponse = new QuestionResponse();

		questionResponse.setMemberId(memberId);
		questionResponse.setCreateTime(new Date());
		questionResponse.setDelState(1);
		questionResponse.setAnswer(2);
		questionResponse.setQuestionId(questionId);

		questionResponseMapper.insertSelective(questionResponse);

	}

	// 教师端给学生举手评分
	@Override
	public void score(Integer questionResponseId, Integer grade) {
		// TODO Auto-generated method stub
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setGrade(grade);
		questionResponse.setAnswer(1);
		QuestionResponseExample questionResponseExample = new QuestionResponseExample();
		questionResponseExample.createCriteria().andResponseIdEqualTo(questionResponseId);

		questionResponseMapper.updateByExampleSelective(questionResponse, questionResponseExample);

	}

	// 教师移动端端查询提问列表
	@Override
	public List<Question> teacherQuestionList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		// 查询待举手问题列表
		Integer classRelationId = Integer.valueOf(map.get("classRelationId").toString());
		Integer state = Integer.valueOf(map.get("state").toString());
		QuestionExample questionExample = new QuestionExample();
		questionExample.createCriteria().andClassRelationIdEqualTo(classRelationId).andDelStateEqualTo(1).andStateEqualTo(state);
		List<Question> questionList = questionMapper.selectByExample(questionExample);
		return questionList;
	}

	//学生端课堂举手数据统计
	@Override
	public List<Map<String, Object>> questionStatisticsForUser(Integer memberId, Integer classRelationId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		QuestionExample questionExample = new QuestionExample();
		questionExample.createCriteria().andClassRelationIdEqualTo(classRelationId).andDelStateEqualTo(1);
		List<Question> questionList = questionMapper.selectByExample(questionExample);
		//未举手
		Integer score0 = 0;
		Integer score1 = 0;
		Integer score2 = 0;
		Integer score3 = 0;
		Integer score4 = 0;
		//举手未回答
		Integer score5 = 0;
		
		if (questionList.size() > 0 && !questionList.isEmpty()) {
			for (Question q:questionList) {
				//查询该用户回答评分情况
				QuestionResponseExample questionResponseExample = new QuestionResponseExample();
				questionResponseExample.createCriteria().andQuestionIdEqualTo(q.getQuestionId()).andMemberIdEqualTo(memberId)
						.andDelStateEqualTo(1);
				List<QuestionResponse> questionResponseList = questionResponseMapper.selectByExample(questionResponseExample);
				if (questionResponseList.size() > 0 && !questionResponseList.isEmpty()) {
					QuestionResponse questionResponse = questionResponseList.get(0);
					if (questionResponse.getAnswer()==2) {
						score5++;
					}else {
						if (questionResponse.getGrade()==1) {
							score1++;
						}
						if (questionResponse.getGrade()==2) {
							score2++;
						}
						if (questionResponse.getGrade()==3) {
							score3++;
						}
						if (questionResponse.getGrade()==4) {
							score4++;
						}
					}
				}else {
					score0++;
				}
				
			}
		}
		Map<String, Object> map0 = new HashMap<>();
		map0.put("name", "未举手");
		map0.put("value", score0);
		mapList.add(map0);
		Map<String, Object> map1 = new HashMap<>();
		map1.put("name", "优秀");
		map1.put("value", score1);
		mapList.add(map1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("name", "良好");
		map2.put("value", score2);
		mapList.add(map2);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("name", "一般");
		map3.put("value", score3);
		mapList.add(map3);
		Map<String, Object> map4 = new HashMap<>();
		map4.put("name", "较差");
		map4.put("value", score4);
		mapList.add(map4);
		Map<String, Object> map5 = new HashMap<>();
		map5.put("name", "未回答");
		map5.put("value", score5);
		mapList.add(map5);
		return mapList;
	}

	//教师端举手数据统计
	@Override
	public Map<String, Object> questionStatisticsForTeacher(Integer classRelationId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		List<String> questionDataList = new ArrayList<>();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		// 查询该课程所有课堂举手
		QuestionExample questionExample = new QuestionExample();
		questionExample.setOrderByClause("modify_time asc");
		questionExample.createCriteria().andDelStateEqualTo(1).andClassRelationIdEqualTo(classRelationId);
		List<Question> questionList = questionMapper.selectByExample(questionExample);

		List<Integer> score0List = new ArrayList<>();
		List<Integer> score1List = new ArrayList<>();
		List<Integer> score2List = new ArrayList<>();
		List<Integer> score3List = new ArrayList<>();
		List<Integer> score4List = new ArrayList<>();
		List<Integer> score5List = new ArrayList<>();

		if (questionList.size() > 0 && !questionList.isEmpty()) {
			for (Question q : questionList) {
				questionDataList.add(q.getQuestionName());
				// 举手未回答
				Integer score0 = 0;
				
				Integer score1 = 0;
				Integer score2 = 0;
				Integer score3 = 0;
				Integer score4 = 0;
				//举手人数
				Integer score5 = 0;
				
				// 查询该课程下的用户列表
				ClassRelationExample classRelationExample = new ClassRelationExample();
				classRelationExample.createCriteria().andIdEqualTo(classRelationId).andDelStateEqualTo(1);
				Integer grades = classRelationMapper.selectByExample(classRelationExample).get(0).getGrades();
				// 根据班级查询人员列表
				MemberExample memberExample = new MemberExample();
				memberExample.createCriteria().andGradesEqualTo(grades);
				List<Member> memberList = memberMapper.selectByExample(memberExample);
				if (memberList.size() > 0 && !memberList.isEmpty()) {
					for (Member m : memberList) {
						// 查询用户测试分数等级
						QuestionResponseExample questionResponseExample = new QuestionResponseExample();
						questionResponseExample.createCriteria().andQuestionIdEqualTo(q.getQuestionId()).andMemberIdEqualTo(m.getMemberId()).andDelStateEqualTo(1);
						List<QuestionResponse> questionR = questionResponseMapper.selectByExample(questionResponseExample);
						if (questionR.size()!=0) {
							score5++;
							if (questionR.get(0).getAnswer()==2) {
								score0++;
							}else {
								if (questionR.get(0).getGrade()==1) {
									score1++;
								}
								if (questionR.get(0).getGrade()==2) {
									score2++;
								}
								if (questionR.get(0).getGrade()==3) {
									score3++;
								}
								if (questionR.get(0).getGrade()==4) {
									score4++;
								}
							}
							
							
						}
						
					}
					
				}
				score0List.add(score0);
				score1List.add(score1);
				score2List.add(score2);
				score3List.add(score3);
				score4List.add(score4);
				score5List.add(score5);
				
			}
		}
		Map<String, Object> map5 = new HashMap<>();
		map5.put("name", "举手人数");
		map5.put("data", score5List);
		mapList.add(map5);
		Map<String, Object> map0 = new HashMap<>();
		map0.put("name", "举手未回答");
		map0.put("data", score0List);
		mapList.add(map0);
		Map<String, Object> map1 = new HashMap<>();
		map1.put("name", "优秀");
		map1.put("data", score1List);
		mapList.add(map1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("name", "良好");
		map2.put("data", score2List);
		mapList.add(map2);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("name", "一般");
		map3.put("data", score3List);
		mapList.add(map3);
		Map<String, Object> map4 = new HashMap<>();
		map4.put("name", "较差");
		map4.put("data", score4List);
		mapList.add(map4);
		
		
		map.put("testDataList", questionDataList);
		map.put("mapList", mapList);
		return map;
	}

}

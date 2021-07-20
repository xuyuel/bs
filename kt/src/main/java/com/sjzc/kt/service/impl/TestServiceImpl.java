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
import com.sjzc.kt.dao.TbstaticMapper;
import com.sjzc.kt.dao.TestAnswerMapper;
import com.sjzc.kt.dao.TestChoiceMapper;
import com.sjzc.kt.dao.TestMapper;
import com.sjzc.kt.dao.TestQuestionMapper;
import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.ClassRelationExample;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.MemberExample;
import com.sjzc.kt.entity.TbstaticExample;
import com.sjzc.kt.entity.Test;
import com.sjzc.kt.entity.TestAnswer;
import com.sjzc.kt.entity.TestAnswerExample;
import com.sjzc.kt.entity.TestChoice;
import com.sjzc.kt.entity.TestChoiceExample;
import com.sjzc.kt.entity.TestExample;
import com.sjzc.kt.entity.TestExample.Criteria;
import com.sjzc.kt.entity.TestQuestion;
import com.sjzc.kt.entity.TestQuestionExample;
import com.sjzc.kt.service.MemberService;
import com.sjzc.kt.service.TestService;

@Service
public class TestServiceImpl implements TestService{

	@Autowired
	private TestMapper testMapper;
	@Autowired
	private TestQuestionMapper testQuestionMapper;
	@Autowired
	private TestChoiceMapper testChoiceMapper;
	@Autowired
	private TestAnswerMapper testAnswerMapper;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ClassRelationMapper classRelationMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private TbstaticMapper staticMapper;
	
	/**  
	 * @Title: addTest
	 * @Description: 添加测试试卷
	 * @author xyl
	 * @date 2021-01-26 
	 */
	@Override
	public void addTest(Test test) {
		// TODO Auto-generated method stub
		test.setCreateTime(new Date());
		test.setDelState(1);
		test.setState(1);//未发布
		//插入试卷
		testMapper.insertSelective(test);
		for (TestQuestion testQuestion:test.getTestQuestionList()) {
			testQuestion.setTestId(test.getTestId());
			testQuestion.setDelState(1);
			testQuestion.setCreateTime(new Date());
			//插入问题
			testQuestionMapper.insertSelective(testQuestion);
			for (TestChoice testChoice:testQuestion.getTestChoiceList()) {
				testChoice.setQuestionId(testQuestion.getQuestionId());
				testChoice.setCreateTime(new Date());;
				testChoice.setDelState(1);
				//插入选项
				testChoiceMapper.insertSelective(testChoice);
			}
		}
	}
	/**  
	 * @Title: selectTeacherTestList
	 * @Description: 教师端查询测试试卷列表 / 教师移动端查看课堂测验列表
	 * @author xyl
	 * @date 2021-01-27 
	 */
	@Override
	public List<Test> selectTeacherTestList(Test test) {
		// TODO Auto-generated method stub
		TestExample testExample = new TestExample();
		Criteria criteria = testExample.createCriteria();
		//时间倒序
		testExample.setOrderByClause("create_time desc");
		criteria.andTeacherIdEqualTo(test.getTeacherId());
		criteria.andDelStateEqualTo(1).andClassRelationIdEqualTo(test.getClassRelationId());
		//条件查询发布状态
		if(null!=test.getState()) {
			criteria.andStateEqualTo(test.getState());
			//已发布根据发布时间倒序
			if (test.getState()==2) {
				testExample.setOrderByClause("modify_time desc");
			}
		}
		//条件查询测试试卷名称
		if (test.getTestName()!=null && !test.getTestName().equals("")) {
			criteria.andTestNameLike("%"+test.getTestName()+"%");
		}
		List<Test> testList = testMapper.selectByExample(testExample);
		//格式化时间戳
		if (testList.size()>0 && !testList.isEmpty()) {
			for (Test t:testList) {
				
				t.setCreateTimeFormat(t.getCreateTime().getTime());
				//发布时间
				if (t.getModifyTime()!=null) {
					t.setReleaseTimeFormat(t.getModifyTime().getTime());
				}
				if (test.getState()==2) {
					Map<String, Object> map = new HashMap<>();
					Integer allNum = 0;
					Integer answerNum = 0;
					//回答人数
					t.getClassRelationId();
					// 根据绑定课程查询班级
					ClassRelationExample classRelationExample = new ClassRelationExample();
					classRelationExample.createCriteria()
							.andIdEqualTo(testMapper.selectByExample(testExample).get(0).getClassRelationId())
							.andDelStateEqualTo(1);
					List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
					Integer grades = classRelationList.get(0).getGrades();
					//查询全部人数
					MemberExample memberExample = new MemberExample();
					com.sjzc.kt.entity.MemberExample.Criteria createCriteria = memberExample.createCriteria();
					//Criteria criteria = memberExample.createCriteria();
					createCriteria.andGradesEqualTo(grades).andDelStateEqualTo(1);
					List<Member> memberList = memberMapper.selectByExample(memberExample);
					allNum = memberList.size();
					//查询已回答人数
					TestAnswerExample testAnswerExample = new TestAnswerExample();
					testAnswerExample.createCriteria().andTestIdEqualTo(t.getTestId()).andDelStateEqualTo(1);
					List<TestAnswer> testAnswerList = testAnswerMapper.selectByExample(testAnswerExample);
					List<Integer> memberIds = new ArrayList<Integer>();
					memberIds.add(0);
					if (testAnswerList.size() > 0 && !testAnswerList.isEmpty()) {
						memberIds.clear();
						for (TestAnswer tA : testAnswerList) {
							if (!memberIds.contains(tA.getMemberId())) {
								memberIds.add(tA.getMemberId());
							}
						}
					}else {
						memberIds.clear();
					}
					answerNum=memberIds.size();
					map.put("allNum", allNum);
					map.put("answerNum", answerNum);
					t.setAnswerMap(map);
				}
			}
		}
		
		return testList;
	}
	/**  
	 * @Title: selectTestDetail
	 * @Description: 查询试卷详情
	 * @author xyl
	 * @date 2021-01-27 
	 */
	@Override
	public Test selectTestDetail(Integer testId) {
		// TODO Auto-generated method stub
		TestExample testExample = new TestExample();
		testExample.createCriteria().andDelStateEqualTo(1).andTestIdEqualTo(testId);
		Test test = testMapper.selectByExample(testExample).get(0);
		//查询问题
		TestQuestionExample testQuestionExample = new TestQuestionExample();
		testQuestionExample.createCriteria().andTestIdEqualTo(test.getTestId()).andDelStateEqualTo(1);
		testQuestionExample.setOrderByClause("sort asc");
		List<TestQuestion> testQuestionList = testQuestionMapper.selectByExample(testQuestionExample);
		for (TestQuestion testQuestion:testQuestionList) {
			TestChoiceExample choiceExample = new TestChoiceExample();
			choiceExample.createCriteria().andQuestionIdEqualTo(testQuestion.getQuestionId()).andDelStateEqualTo(1);
			choiceExample.setOrderByClause("choice_sort asc");
			List<TestChoice> testChoiceList = testChoiceMapper.selectByExample(choiceExample);
			testQuestion.setTestChoiceList(testChoiceList);
		}
		test.setTestQuestionList(testQuestionList);
		return test;
	}
	/**  
	 * @Title: selectMemberTestList
	 * @Description: 学生端查询测试试卷列表 //弃用
	 * @author xyl
	 * @date 2021-01-27 
	 */
	@Override
	public List<Test> selectMemberTestList() {
		// TODO Auto-generated method stub
		//查询当前用户的课程idList
		//用户id
		Integer memberId = 1;
		List<Integer> courseIdList = new ArrayList<>();
		courseIdList.add(1);
		courseIdList.add(2);
		TestExample testExample = new TestExample();
		testExample.createCriteria().andScheduleIdIn(courseIdList).andDelStateEqualTo(1).andStateEqualTo(2);
		List<Test> testList = testMapper.selectByExample(testExample);
		//查询该试卷该用户是否已经回答
		if (testList.size() > 0 && !testList.isEmpty()) {
			for (int i=0;i<testList.size();i++) {
				TestAnswerExample testAnswerExample = new TestAnswerExample();
				testAnswerExample.createCriteria().andTestIdEqualTo(testList.get(i).getTestId()).andMemberIdEqualTo(memberId);
				List<TestAnswer> testAnswerList = testAnswerMapper.selectByExample(testAnswerExample);
				if (testAnswerList.size() > 0 && !testAnswerList.isEmpty()) {
					testList.remove(i);
				}
			}
		}
		return testList;
	}
	/**  
	 * @Title: answerTest
	 * @Description: 学生回答测试
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@Override
	public void answerTest(Test test) {
		// TODO Auto-generated method stub
		for (TestQuestion testQuestion:test.getTestQuestionList()) {
			TestAnswer testAnswer = new TestAnswer();
			testAnswer.setMemberId(test.getMemberId());
			testAnswer.setQuestionId(testQuestion.getQuestionId());
			testAnswer.setTestId(test.getTestId());
			testAnswer.setCreateTime(new Date());
			testAnswer.setDelState(1);
			testAnswer.setChoiceId(testQuestion.getChoiceId());
			/*for (TestChoice testChoice:testQuestion.getChoiceId()) {
				if (testChoice.getSelectPoint()==1) {
					testAnswer.setChoiceId(testChoice.getChoiceId());
				}
			}*/
			testAnswerMapper.insertSelective(testAnswer);
		}
		
	}
	/**  
	 * @Title: releaseTest
	 * @Description: 发布试卷
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@Override
	public void releaseTest(Integer testId) {
		// TODO Auto-generated method stub
		Test test = new Test();
		TestExample testExample = new TestExample();
		testExample.createCriteria().andTestIdEqualTo(testId);
		test.setState(2);
		test.setModifyTime(new Date());
		testMapper.updateByExampleSelective(test, testExample);
	}
	/**  
	 * @Title: delTest
	 * @Description: 删除试卷
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@Override
	public void delTest(Integer testId) {
		// TODO Auto-generated method stub
		Test test = new Test();
		TestExample testExample = new TestExample();
		testExample.createCriteria().andTestIdEqualTo(testId);
		test.setDelState(2);
		testMapper.updateByExampleSelective(test, testExample);
	}
	////后台管理教师查询用户回答试卷详情 //          学生移动端查询试卷回答详情（暂时弃用）
	@Override
	public Test testUserDetail(Integer testId, Integer memberId) {
		// TODO Auto-generated method stub
		TestExample testExample = new TestExample();
		testExample.createCriteria().andDelStateEqualTo(1).andTestIdEqualTo(testId);
		Test test = testMapper.selectByExample(testExample).get(0);
		//查询问题
		TestQuestionExample testQuestionExample = new TestQuestionExample();
		testQuestionExample.createCriteria().andTestIdEqualTo(test.getTestId()).andDelStateEqualTo(1);
		testQuestionExample.setOrderByClause("sort asc");
		List<TestQuestion> testQuestionList = testQuestionMapper.selectByExample(testQuestionExample);
		for (TestQuestion testQuestion:testQuestionList) {
			TestChoiceExample choiceExample = new TestChoiceExample();
			choiceExample.createCriteria().andQuestionIdEqualTo(testQuestion.getQuestionId()).andDelStateEqualTo(1);
			choiceExample.setOrderByClause("choice_sort asc");
			List<TestChoice> testChoiceList = testChoiceMapper.selectByExample(choiceExample);
			
			testQuestion.setTestChoiceList(testChoiceList);
			//查询用户回答
			TestAnswerExample testAnswerExample = new TestAnswerExample();
			testAnswerExample.createCriteria().andQuestionIdEqualTo(testQuestion.getQuestionId()).andMemberIdEqualTo(memberId).andDelStateEqualTo(1);
			List<TestAnswer> answerList = testAnswerMapper.selectByExample(testAnswerExample);
			Map<String, Object> map = new HashMap<String, Object>();
			//
			TestChoiceExample choiceExample2 = new TestChoiceExample();
			choiceExample2.createCriteria().andChoiceIdEqualTo(answerList.get(0).getChoiceId()).andDelStateEqualTo(1);
			List<TestChoice> memberChoiceList = testChoiceMapper.selectByExample(choiceExample);
			
			if (answerList.size()==0) {
				map.put("myAnswer", "");
				map.put("myAnswerId", "");
				map.put("rightAnswer", 2);
			}else {
				//根据选项id查询选项名称
				map.put("rightAnswer", 2);
				for(TestChoice t:testChoiceList) {
					if (t.getChoiceId().equals(answerList.get(0).getChoiceId())) {
						map.put("myAnswer", t.getChoiceContent());
						if (t.getRightAnswer()==1) {
							map.put("rightAnswer", 1);
						}
					}
				}
				/*//map.put("myAnswer", answerList.get(0).getChoiceContent());
				map.put("myAnswerId", answerList.get(0).getChoiceId());
				map.put("myAnswer", memberChoiceList.get(0).getChoiceContent());
				map.put("myAnswerId", memberChoiceList.get(0).getChoiceId());
				if (memberChoiceList.get(0).getRightAnswer()==1) {
					map.put("rightAnswer", 1);
				}else {
					map.put("rightAnswer", 2);
				}*/
				
				testQuestion.setAnswerMap(map);
			}
		}
		test.setTestQuestionList(testQuestionList);
		Map<String, Object> map1 = new HashMap<>();
		map1.put("score", selectTestScore(memberId, testId));
		test.setAnswerMap(map1);
		return test;
	}
	//移动端用户查询自己的课堂测试列表
	@Override
	public List<Test> TestListForMember(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		//用户id
		Integer memberId = Integer.valueOf(map.get("memberId").toString());
		//课程关联id
		Integer classRelationId = Integer.valueOf(map.get("classRelationId").toString());
		
		//查询该用户的全部课堂测验列表
		TestExample testExample = new TestExample();
		testExample.setOrderByClause("modify_time desc");
		testExample.createCriteria().andDelStateEqualTo(1).andClassRelationIdEqualTo(classRelationId).andStateEqualTo(2);
		List<Test> testList = testMapper.selectByExample(testExample);
		
		//已完成测验列表 显示测验等级
		if ("2".equals(map.get("state").toString())) {
			
			if (testList.size()>0 && !testList.isEmpty()) {
				for (int i = testList.size() - 1; i >= 0; i--) {
					//格式化时间戳
					testList.get(i).setReleaseTimeFormat(testList.get(i).getModifyTime().getTime());
					//查询该用户回答
					TestAnswerExample testAnswerExample1 = new TestAnswerExample();
					testAnswerExample1.createCriteria().andTestIdEqualTo(testList.get(i).getTestId()).andDelStateEqualTo(1).andMemberIdEqualTo(memberId);
					List<TestAnswer> testAnswerList = testAnswerMapper.selectByExample(testAnswerExample1);
					//如果回答为空，去除
					if (testAnswerList.size()==0) {
						testList.remove(i);
					}else {
						//不为空查询成绩
						Integer score = selectTestScore(memberId,testList.get(i).getTestId());
						Map<String, Object> answerMap = new HashMap<String, Object>();
						answerMap.put("score", score);
						testList.get(i).setAnswerMap(answerMap);
						//回答时间
						testList.get(i).setAnswerTimeFormat(testAnswerList.get(0).getCreateTime().getTime());
						testList.get(i).setReleaseTimeFormat(testList.get(i).getModifyTime().getTime());
					}
				}
			}
		}
		//未完成列表
		Map<String, Object> answerMap = new HashMap<String, Object>();
		if ("1".equals(map.get("state").toString())) {
			if (testList.size()>0 && !testList.isEmpty()) {
				for (int i = testList.size() - 1; i >= 0; i--) {
					//查询该用户回答
					TestAnswerExample testAnswerExample1 = new TestAnswerExample();
					testAnswerExample1.createCriteria().andTestIdEqualTo(testList.get(i).getTestId()).andDelStateEqualTo(1).andMemberIdEqualTo(memberId);
					List<TestAnswer> testAnswerList = testAnswerMapper.selectByExample(testAnswerExample1);
					//如果回答不为空，去除
					if (testAnswerList.size()==0) {
						//添加是否回答属性
						//Map<String, Object> answerMap = new HashMap<String, Object>();
						answerMap.put("score", 0);
						testList.get(i).setAnswerMap(answerMap);
						testList.get(i).setReleaseTimeFormat(testList.get(i).getModifyTime().getTime());
					}else {
						testList.remove(i);
					}
					
				}
			}
			
			
		}
		return testList;
	}
	//根据测试试卷id用户id查询试卷成绩
	@Override
	public Integer selectTestScore(Integer memberId, Integer testId) {
		// TODO Auto-generated method stub
		TestAnswerExample testAnswerExample1 = new TestAnswerExample();
		testAnswerExample1.createCriteria().andTestIdEqualTo(testId).andDelStateEqualTo(1).andMemberIdEqualTo(memberId);
		List<TestAnswer> testAnswerList = testAnswerMapper.selectByExample(testAnswerExample1);
		//等级
		Integer grade = 0;
		if (testAnswerList.size()==0) {
			return grade;
		}
		//问题数
		double allNumber = testAnswerList.size();
		//正确问题数
		double rightNumber = 0;
		
		for (TestAnswer a:testAnswerList) {
			//查询该回答是否为正确答案
			TestChoiceExample testChoiceExample = new TestChoiceExample();
			testChoiceExample.createCriteria().andChoiceIdEqualTo(a.getChoiceId()).andRightAnswerEqualTo(1).andDelStateEqualTo(1);
			List<TestChoice> testChoice = testChoiceMapper.selectByExample(testChoiceExample);
			if (testChoice.size()>0 && !testChoice.isEmpty()) {
				rightNumber++;
			}
		}
		//
		Double score1 = rightNumber/allNumber*100;
		//Integer score = ;
		int score = Integer.valueOf(score1.intValue());

		//1优秀 2良好 3一般 4较差
		grade=4;
		if (score>=60) {
			grade=3;
		}
		if (score>=70) {
			grade=2;
		}
		if (score>=85) {
			grade=1;
		}
		return grade;
	}
	//教师移动端端查看试卷详情统计
	@Override
	public Test testStatistics(Integer testId) {
		// TODO Auto-generated method stub
		TestExample testExample = new TestExample();
		testExample.createCriteria().andDelStateEqualTo(1).andTestIdEqualTo(testId);
		Test test = testMapper.selectByExample(testExample).get(0);
		//查询分数等级人数
		Integer score1 = 0;
		Integer score2 = 0;
		Integer score3 = 0;
		Integer score4 = 0;
		//总人数
		Integer allNum = 0;
		//已回答人数
		Integer answerNum = 0;
		//未回答人数
		Integer noAnswerNum = 0;
		//查询该试卷的得分情况
		test.getClassRelationId();
		List<Member> memberList = memberService.selectMemberByCRId(test.getClassRelationId());
		//维护总人数
		allNum=memberList.size();
		if (memberList.size()>0 && !memberList.isEmpty()) {
			for(Member m:memberList) {
				//查询得分情况
				Integer memberScore = selectTestScore(m.getMemberId(), test.getTestId());
				if (memberScore==0) {
					noAnswerNum++;
				}
				if (memberScore==1) {
					score1++;
				}
				if (memberScore==2) {
					score2++;
				}
				if (memberScore==3) {
					score3++;
				}
				if (memberScore==4) {
					score4++;
				}
			}
			
		}
		//维护已回答人数
		answerNum=allNum-noAnswerNum;
		Map<String, Object> testMap = new HashMap<>();
		testMap.put("score1", score1);
		testMap.put("score2", score2);
		testMap.put("score3", score3);
		testMap.put("score4", score4);
		testMap.put("allNum", allNum);
		testMap.put("answerNum", answerNum);
		testMap.put("noAnswerNum", noAnswerNum);
		test.setAnswerMap(testMap);
		//查询问题
		TestQuestionExample testQuestionExample = new TestQuestionExample();
		testQuestionExample.createCriteria().andTestIdEqualTo(test.getTestId()).andDelStateEqualTo(1);
		testQuestionExample.setOrderByClause("sort asc");
		List<TestQuestion> testQuestionList = testQuestionMapper.selectByExample(testQuestionExample);
		for (TestQuestion testQuestion:testQuestionList) {
			TestChoiceExample choiceExample = new TestChoiceExample();
			choiceExample.createCriteria().andQuestionIdEqualTo(testQuestion.getQuestionId()).andDelStateEqualTo(1);
			choiceExample.setOrderByClause("choice_sort asc");
			List<TestChoice> testChoiceList = testChoiceMapper.selectByExample(choiceExample);
			testQuestion.setTestChoiceList(testChoiceList);
			for (TestChoice c:testChoiceList) {
				//查询每一选项回答人数
				TestAnswerExample testAnswerExample = new TestAnswerExample();
				testAnswerExample.createCriteria().andChoiceIdEqualTo(c.getChoiceId());
				Integer selectCount= testAnswerMapper.countByExample(testAnswerExample);
				c.setSelectPoint(selectCount);
			}
			
		}
		test.setTestQuestionList(testQuestionList);
		return test;
	}
	//学生移动端查看用户回答详情
	@Override
	public Test testDetailForUser(Integer testId, Integer memberId) {

		TestExample testExample = new TestExample();
		testExample.createCriteria().andDelStateEqualTo(1).andTestIdEqualTo(testId);
		Test test = testMapper.selectByExample(testExample).get(0);
		// 查询问题
		TestQuestionExample testQuestionExample = new TestQuestionExample();
		testQuestionExample.createCriteria().andTestIdEqualTo(test.getTestId()).andDelStateEqualTo(1);
		testQuestionExample.setOrderByClause("sort asc");
		List<TestQuestion> testQuestionList = testQuestionMapper.selectByExample(testQuestionExample);
		for (TestQuestion testQuestion : testQuestionList) {
			TestChoiceExample choiceExample = new TestChoiceExample();
			choiceExample.createCriteria().andQuestionIdEqualTo(testQuestion.getQuestionId()).andDelStateEqualTo(1);
			choiceExample.setOrderByClause("choice_sort asc");
			List<TestChoice> testChoiceList = testChoiceMapper.selectByExample(choiceExample);

			testQuestion.setTestChoiceList(testChoiceList);
			// 查询用户回答
			TestAnswerExample testAnswerExample = new TestAnswerExample();
			testAnswerExample.createCriteria().andQuestionIdEqualTo(testQuestion.getQuestionId())
					.andMemberIdEqualTo(memberId).andDelStateEqualTo(1);
			List<TestAnswer> answerList = testAnswerMapper.selectByExample(testAnswerExample);
			Map<String, Object> map = new HashMap<String, Object>();
			//
			TestChoiceExample choiceExample2 = new TestChoiceExample();
			choiceExample2.createCriteria().andChoiceIdEqualTo(answerList.get(0).getChoiceId()).andDelStateEqualTo(1);
			List<TestChoice> memberChoiceList = testChoiceMapper.selectByExample(choiceExample2);

			if (answerList.size() == 0) {
				map.put("myAnswer", "");
				map.put("myAnswerId", "");
				map.put("rightAnswer", 2);
			} else {
				//根据选项id查询选项名称
				map.put("rightAnswer", 2);
				for(TestChoice t:testChoiceList) {
					if (answerList.get(0).getChoiceId().equals(t.getChoiceId())) {
						map.put("myAnswer", t.getChoiceContent());
						if (t.getRightAnswer()==1) {
							map.put("rightAnswer", 1);
						}
					}else {
						map.put("myAnswer", memberChoiceList.get(0).getChoiceContent());
					}
				}
				//map.put("myAnswer", answerList.get(0).getChoiceContent());
				map.put("myAnswerId", answerList.get(0).getChoiceId());
				/*if (memberChoiceList.get(0).getRightAnswer() == 1) {
					map.put("rightAnswer", 1);
				} else {
					map.put("rightAnswer", 2);
				}*/

				testQuestion.setAnswerMap(map);
			}
		}
		test.setTestQuestionList(testQuestionList);
		Map<String, Object> map1 = new HashMap<>();
		map1.put("score", selectTestScore(memberId, testId));
		test.setAnswerMap(map1);
		return test;
	}
	
	//学生端课堂测验数据统计
	@Override
	public List<Map<String, Object>> testStatisticsForUser(Integer memberId,Integer classRelationId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		//查询该用户的所有课堂测验
		TestExample testExample = new TestExample();
		testExample.createCriteria().andDelStateEqualTo(1).andClassRelationIdEqualTo(classRelationId).andStateEqualTo(2);
		List<Test> testList = testMapper.selectByExample(testExample);
		
		Integer score0 = 0;
		Integer score1 = 0;
		Integer score2 = 0;
		Integer score3 = 0;
		Integer score4 = 0;
		
		if (testList.size()>0 && !testList.isEmpty()) {
			for (Test t:testList) {
				
				//查询用户测试分数等级
				Integer score = selectTestScore(memberId,t.getTestId());
				if (score==0) {
					score0++;
				}
				if (score==1) {
					score1++;
				}
				if (score==2) {
					score2++;
				}
				if (score==3) {
					score3++;
				}
				if (score==4) {
					score4++;
				}
			}
		}
		Map<String, Object> map0 = new HashMap<>();
		map0.put("name", "未完成");
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
		return mapList;
	}
	
	//教师端课堂测验数据统计
	@Override
	public Map<String, Object> testStatisticsForTeacher(Integer classRelationId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		List<String> testDataList = new ArrayList<>();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		// 查询该课程所有课堂测验
		TestExample testExample = new TestExample();
		testExample.setOrderByClause("modify_time asc");
		testExample.createCriteria().andDelStateEqualTo(1).andClassRelationIdEqualTo(classRelationId)
				.andStateEqualTo(2);
		List<Test> testList = testMapper.selectByExample(testExample);

		List<Integer> score0List = new ArrayList<>();
		List<Integer> score1List = new ArrayList<>();
		List<Integer> score2List = new ArrayList<>();
		List<Integer> score3List = new ArrayList<>();
		List<Integer> score4List = new ArrayList<>();

		if (testList.size() > 0 && !testList.isEmpty()) {
			for (Test t : testList) {
				testDataList.add(t.getTestName());
				// 未答题
				Integer score0 = 0;
				Integer score1 = 0;
				Integer score2 = 0;
				Integer score3 = 0;
				Integer score4 = 0;
				
				// 查询该课程下的用户列表
				ClassRelationExample classRelationExample = new ClassRelationExample();
				classRelationExample.createCriteria().andIdEqualTo(classRelationId);
				Integer grades = classRelationMapper.selectByExample(classRelationExample).get(0).getGrades();
				// 根据班级查询人员列表
				MemberExample memberExample = new MemberExample();
				memberExample.createCriteria().andGradesEqualTo(grades);
				List<Member> memberList = memberMapper.selectByExample(memberExample);
				if (memberList.size() > 0 && !memberList.isEmpty()) {
					for (Member m : memberList) {
						// 查询用户测试分数等级
						Integer score = selectTestScore(m.getMemberId(), t.getTestId());
						if (score == 0) {
							score0++;
						}
						if (score == 1) {
							score1++;
						}
						if (score == 2) {
							score2++;
						}
						if (score == 3) {
							score3++;
						}
						if (score == 4) {
							score4++;
						}
					}
					
				}
				score0List.add(score0);
				score1List.add(score1);
				score2List.add(score2);
				score3List.add(score3);
				score4List.add(score4);
				
			}
		}
		Map<String, Object> map0 = new HashMap<>();
		map0.put("name", "未完成");
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

		map.put("testDataList", testDataList);
		map.put("mapList", mapList);
		return map;
	}
	//教师端根据测验id查询学生完成列表
	@Override
	public List<Map<String, Object>> memberTestListForTeacher(Integer grades,Integer testId,Integer state,List<Integer> memberIdList) {
		// TODO Auto-generated method stub
		//1已完成
		if (state == 1) {
			List<Map<String, Object>> memberTestList = testAnswerMapper.memberTestListForTeacher(testId);
			//查询成绩
			if (memberTestList.size() > 0 && !memberTestList.isEmpty()) {
				for (Map<String, Object> m:memberTestList) {
					//查询用户信息
					TbstaticExample staticExample1 = new TbstaticExample();
					staticExample1.createCriteria().andIdEqualTo(Integer.valueOf(m.get("department").toString())).andDelStateEqualTo(1);
					m.put("departmentFormat", staticMapper.selectByExample(staticExample1).get(0).getDescription());
					
					TbstaticExample staticExample2 = new TbstaticExample();
					staticExample2.createCriteria().andIdEqualTo(Integer.valueOf(m.get("major").toString())).andDelStateEqualTo(1);
					m.put("majorFormat", staticMapper.selectByExample(staticExample2).get(0).getDescription());
					
					TbstaticExample staticExample3 = new TbstaticExample();
					staticExample3.createCriteria().andIdEqualTo(Integer.valueOf(m.get("grades").toString())).andDelStateEqualTo(1);
					m.put("gradesFormat", staticMapper.selectByExample(staticExample3).get(0).getDescription());
					
					Integer score = selectTestScore(Integer.valueOf(m.get("memberId").toString()), testId);
					m.put("score", score);
				}
			}
			return memberTestList;
		}else {
			List<Map<String, Object>> memberTestList = testAnswerMapper.memberTestListNOForTeacher(grades,memberIdList);
			if (memberTestList.size() > 0 && !memberTestList.isEmpty()) {
				for (Map<String, Object> m:memberTestList) {
					//查询用户信息
					TbstaticExample staticExample1 = new TbstaticExample();
					staticExample1.createCriteria().andIdEqualTo(Integer.valueOf(m.get("department").toString())).andDelStateEqualTo(1);
					m.put("departmentFormat", staticMapper.selectByExample(staticExample1).get(0).getDescription());
					
					TbstaticExample staticExample2 = new TbstaticExample();
					staticExample2.createCriteria().andIdEqualTo(Integer.valueOf(m.get("major").toString())).andDelStateEqualTo(1);
					m.put("majorFormat", staticMapper.selectByExample(staticExample2).get(0).getDescription());
					
					TbstaticExample staticExample3 = new TbstaticExample();
					staticExample3.createCriteria().andIdEqualTo(Integer.valueOf(m.get("grades").toString())).andDelStateEqualTo(1);
					m.put("gradesFormat", staticMapper.selectByExample(staticExample3).get(0).getDescription());
					m.put("score", 0);
				}
			}
			return memberTestList;
		}
		
	}

	
	
}

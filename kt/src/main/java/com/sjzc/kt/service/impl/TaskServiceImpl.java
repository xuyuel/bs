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
import com.sjzc.kt.dao.TaskAnswerMapper;
import com.sjzc.kt.dao.TaskMapper;
import com.sjzc.kt.dao.TaskQuestionMapper;
import com.sjzc.kt.dao.TbstaticMapper;
import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.ClassRelationExample;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.MemberExample;
import com.sjzc.kt.entity.Task;
import com.sjzc.kt.entity.TaskAnswer;
import com.sjzc.kt.entity.TaskAnswerExample;
import com.sjzc.kt.entity.TaskExample;
import com.sjzc.kt.entity.TaskExample.Criteria;
import com.sjzc.kt.entity.TaskQuestion;
import com.sjzc.kt.entity.TaskQuestionExample;
import com.sjzc.kt.entity.TbstaticExample;
import com.sjzc.kt.entity.Test;
import com.sjzc.kt.entity.TestAnswer;
import com.sjzc.kt.entity.TestAnswerExample;
import com.sjzc.kt.entity.TestChoice;
import com.sjzc.kt.entity.TestChoiceExample;
import com.sjzc.kt.entity.TestExample;
import com.sjzc.kt.entity.TestQuestion;
import com.sjzc.kt.entity.TestQuestionExample;
import com.sjzc.kt.service.MemberService;
import com.sjzc.kt.service.TaskService;
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private TaskQuestionMapper taskQuestionMapper;
	@Autowired
	private TaskAnswerMapper taskAnswerMapper;
	@Autowired
	private ClassRelationMapper classRelationMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private TbstaticMapper staticMapper;
	@Autowired
	private MemberService memberService;
	
	/**  
	 * @Title: addTask
	 * @Description: 添加课后作业
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@Override
	public void addTask(Task task) {
		// TODO Auto-generated method stub
		task.setCreateTime(new Date());
		task.setDelState(1);
		// 时间戳转化
		task.setStartTime(new Date(task.getStartTimeFormat()*1000));
		task.setEndTime(new Date(task.getEndTimeFormat()*1000));
		Date d = new Date(task.getStartTimeFormat()*1000);
		taskMapper.insertSelective(task);
		for (TaskQuestion taskQuestion:task.getTaskQuestionsList()) {
			taskQuestion.setCreateTime(new Date());
			taskQuestion.setDelState(1);
			taskQuestion.setTaskId(task.getTaskId());
			taskQuestionMapper.insertSelective(taskQuestion);
		}
		
	}

	/**  
	 * @Title: slectTeacherTaskList
	 * @Description: 教师端查询课后作业列表
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@Override
	public List<Task> slectTeacherTaskList(Task task) {
		// TODO Auto-generated method stub
		TaskExample taskExample = new TaskExample();
		Criteria criteria = taskExample.createCriteria();
		//时间倒序
		taskExample.setOrderByClause("create_time desc");
		criteria.andTeacherIdEqualTo(task.getTeacherId()).andClassRelationIdEqualTo(task.getClassRelationId());
		criteria.andDelStateEqualTo(1);
		/*//时间戳转化
		task.setStartTime(new Date(task.getStartTimeFormat()));
		task.setEndTime(new Date(task.getEndTimeFormat()));*/
		//条件查询发布状态
		if(task.getState()!=null) {
			//未发布
			if (task.getState()==1) {
				criteria.andStartTimeGreaterThan(new Date());
			}
			//进行中
			if (task.getState()==2) {
				criteria.andStartTimeLessThan(new Date()).andEndTimeGreaterThan(new Date());
			}
			//已结束
			if (task.getState()==3) {
				criteria.andEndTimeLessThan(new Date());
			}
		}
		//条件查询测试试卷名称
		if (task.getTitle()!=null && !task.getTitle().equals("")) {
			criteria.andTitleLike("%"+task.getTitle()+"%");
		}
		List<Task> testList = taskMapper.selectByExample(taskExample);
		//格式化时间
		if (testList.size()>0 && !testList.isEmpty()) {
			for (Task t:testList) {
				t.setState(task.getState());
				t.setCreateTimeFormat(t.getCreateTime().getTime());
				t.setStartTimeFormat(t.getStartTime().getTime());
				t.setEndTimeFormat(t.getEndTime().getTime());
			}
		}
		return testList;
	}

	// 学生端查询已发布课后作业列表
	@Override
	public List<Task> slectMemberTaskList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		// 用户id
		Integer memberId = Integer.valueOf(map.get("memberId").toString());
		// 课程关联id
		Integer classRelationId = Integer.valueOf(map.get("classRelationId").toString());
		// 作业状态
		Integer state = Integer.valueOf(map.get("state").toString());

		// 查询该用户所有课程安排id
		List<Integer> courseIdList = new ArrayList<Integer>();
		courseIdList.add(1);
		courseIdList.add(2);
		// 查询待完成作业列表
		if (state == 1) {
			TaskExample taskExample = new TaskExample();
			taskExample.setOrderByClause("start_time desc");
			taskExample.createCriteria().andDelStateEqualTo(1).andClassRelationIdEqualTo(classRelationId)
					.andStartTimeLessThan(new Date()).andEndTimeGreaterThan(new Date());
			List<Task> taskList = taskMapper.selectByExample(taskExample);
			if (taskList.size()>0 && !taskList.isEmpty()) {
				int size = taskList.size();
				for(int i=size-1;i>=0;i--) {
					//查询该试卷是否完成
					TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
					taskAnswerExample.createCriteria().andTaskIdEqualTo(taskList.get(i).getTaskId()).andMemberIdEqualTo(memberId).andDelStateEqualTo(1);
					taskList.get(i).setStartTimeFormat(taskList.get(i).getStartTime().getTime());
					taskList.get(i).setEndTimeFormat(taskList.get(i).getEndTime().getTime());
					
					if (taskAnswerMapper.countByExample(taskAnswerExample)!=0) {
						taskList.remove(i);
					}
				}
			}
			return taskList;
		}
		//已完成列表
		if (state == 2) {
			TaskExample taskExample = new TaskExample();
			taskExample.setOrderByClause("start_time desc");
			taskExample.createCriteria().andDelStateEqualTo(1).andClassRelationIdEqualTo(classRelationId);
			List<Task> taskList = taskMapper.selectByExample(taskExample);
			if (taskList.size()>0 && !taskList.isEmpty()) {
				int size = taskList.size();
				for(int i=size-1;i>=0;i--) {
					//查询该试卷是否完成
					TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
					taskAnswerExample.createCriteria().andTaskIdEqualTo(taskList.get(i).getTaskId()).andMemberIdEqualTo(memberId).andDelStateEqualTo(1);
					taskList.get(i).setStartTimeFormat(taskList.get(i).getStartTime().getTime());
					taskList.get(i).setEndTimeFormat(taskList.get(i).getEndTime().getTime());
					
					if (taskAnswerMapper.countByExample(taskAnswerExample)==0) {
						taskList.remove(i);
					}else {
						//查询课后作业分数
						Integer score = selectTaskScore(memberId, taskList.get(i).getTaskId());
						taskList.get(i).setScore(score);
					}
				}
			}
			return taskList;
		}
		//过期未完成
		if (state == 3) {
			TaskExample taskExample = new TaskExample();
			taskExample.setOrderByClause("start_time desc");
			taskExample.createCriteria().andDelStateEqualTo(1).andClassRelationIdEqualTo(classRelationId).andEndTimeLessThan(new Date());
			List<Task> taskList = taskMapper.selectByExample(taskExample);
			if (taskList.size()>0 && !taskList.isEmpty()) {
				int size = taskList.size();
				for(int i=size-1;i>=0;i--) {
					//查询该试卷是否完成
					TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
					taskAnswerExample.createCriteria().andTaskIdEqualTo(taskList.get(i).getTaskId()).andMemberIdEqualTo(memberId).andDelStateEqualTo(1);
					taskList.get(i).setStartTimeFormat(taskList.get(i).getStartTime().getTime());
					taskList.get(i).setEndTimeFormat(taskList.get(i).getEndTime().getTime());
					
					if (taskAnswerMapper.countByExample(taskAnswerExample)!=0) {
						taskList.remove(i);
					}else {
						//查询课后作业分数
						taskList.get(i).setScore(5);
					}
				}
			}
			return taskList;
		}
		return null;
	}

	/**  
	 * @Title: selectTaskjDetail
	 * @Description: 查询课后作业详情
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@Override
	public Task selectTaskDetail(Integer taskId) {
		// TODO Auto-generated method stub
		TaskExample taskExample = new TaskExample();
		taskExample.createCriteria().andTaskIdEqualTo(taskId).andDelStateEqualTo(1);
		Task task = taskMapper.selectByExample(taskExample).get(0);
		//格式化时间
		task.setCreateTimeFormat(task.getCreateTime().getTime());
		task.setStartTimeFormat(task.getStartTime().getTime());
		task.setEndTimeFormat(task.getEndTime().getTime());
		//查询问题
		TaskQuestionExample taskQuestionExample = new TaskQuestionExample();
		taskQuestionExample.createCriteria().andTaskIdEqualTo(taskId).andDelStateEqualTo(1);
		List<TaskQuestion> taskQuestionsList = taskQuestionMapper.selectByExample(taskQuestionExample);
		task.setTaskQuestionsList(taskQuestionsList);
		return task;
	}

	/**  
	 * @Title: delTask
	 * @Description: 删除课后作业
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@Override
	public void delTask(Integer taskId) {
		// TODO Auto-generated method stub
		TaskExample taskExample = new TaskExample();
		taskExample.createCriteria().andTaskIdEqualTo(taskId);
		Task task = new Task();
		task.setDelState(2);
		taskMapper.updateByExampleSelective(task, taskExample);

	}

	/**  
	 * @Title: answerTask
	 * @Description: 学生回答课后作业
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@Override
	public void answerTask(Task task) {
		// TODO Auto-generated method stub
		//用户id
		Integer memberId = task.getMemberId();
		
		TaskAnswer taskAnswer = new TaskAnswer();
		taskAnswer.setCreateTime(new Date());
		taskAnswer.setDelState(1);
		taskAnswer.setMemberId(memberId);
		taskAnswer.setTaskId(task.getTaskId());
		taskAnswer.setAnswer(task.getTaskAnswer());
		taskAnswerMapper.insertSelective(taskAnswer);
	}

	//查询课后作业学生回答详情
	@Override
	public Task MemberTaskDetail(Integer taskId, Integer memberId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		//查询课后作业详情
		TaskExample taskExample = new TaskExample();
		taskExample.createCriteria().andTaskIdEqualTo(taskId).andDelStateEqualTo(1);
		Task task = taskMapper.selectByExample(taskExample).get(0);
		//查询问题
		// 格式化时间
		task.setCreateTimeFormat(task.getCreateTime().getTime());
		task.setStartTimeFormat(task.getStartTime().getTime());
		task.setEndTimeFormat(task.getEndTime().getTime());
		// 查询问题
		TaskQuestionExample taskQuestionExample = new TaskQuestionExample();
		taskQuestionExample.createCriteria().andTaskIdEqualTo(taskId).andDelStateEqualTo(1);
		List<TaskQuestion> taskQuestionsList = taskQuestionMapper.selectByExample(taskQuestionExample);
		task.setTaskQuestionsList(taskQuestionsList);
		//查询用户回答内容
		TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
		taskAnswerExample.createCriteria().andTaskIdEqualTo(taskId).andMemberIdEqualTo(memberId);
		TaskAnswer taskAnswer = taskAnswerMapper.selectByExample(taskAnswerExample).get(0);
		task.setTaskAnswer(taskAnswer.getAnswer());
		task.setScore(taskAnswer.getEvaluate());
		if (null!=taskAnswer.getEvaluate()) {
			map.put("correctionTime", taskAnswer.getModifyTime().getTime());
		}
		task.setMap(map);
		return task;
	}

	//教师评价课后作业
	@Override
	public void evaluateTask(Integer taskId, Integer memberId,Integer evaluate) {
		// TODO Auto-generated method stub
		TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
		taskAnswerExample.createCriteria().andTaskIdEqualTo(taskId).andMemberIdEqualTo(memberId).andDelStateEqualTo(1);
		TaskAnswer taskAnswer = new TaskAnswer();
		taskAnswer.setEvaluate(evaluate);
		taskAnswer.setModifyTime(new Date());
		taskAnswerMapper.updateByExampleSelective(taskAnswer, taskAnswerExample);
	}
	
	//查询课后作业分数
	public Integer selectTaskScore(Integer memberId,Integer taskId) {
		
		Integer score = 0;
		TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
		taskAnswerExample.createCriteria().andTaskIdEqualTo(taskId).andMemberIdEqualTo(memberId).andDelStateEqualTo(1);
		List<TaskAnswer> answerList = taskAnswerMapper.selectByExample(taskAnswerExample);
		if (answerList.size()>0 && !answerList.isEmpty()) {
			score = answerList.get(0).getEvaluate();
		}
		return score;
	}

	//教师移动端查看课后作业列表
	@Override
	public List<Task> TaskListForTeacher(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer state = Integer.valueOf(map.get("state").toString());
		Integer classRelationId = Integer.valueOf(map.get("classRelationId").toString());
		Integer teacherId = Integer.valueOf(map.get("teacherId").toString());
		TaskExample taskExample = new TaskExample();
		Criteria criteria = taskExample.createCriteria();
		// 时间倒序
		taskExample.setOrderByClause("start_time desc");
		criteria.andTeacherIdEqualTo(teacherId);
		criteria.andDelStateEqualTo(1);
		criteria.andClassRelationIdEqualTo(classRelationId);
		if (state != null) {
			// 进行中
			if (state == 2) {
				criteria.andStartTimeLessThan(new Date()).andEndTimeGreaterThan(new Date());
			}
			// 已结束
			if (state == 3) {
				criteria.andEndTimeLessThan(new Date());
			}
		}
		List<Task> TaskList = taskMapper.selectByExample(taskExample);
		if (TaskList.size() > 0 && !TaskList.isEmpty()) {
			for (Task t : TaskList) {
				t.setCreateTimeFormat(t.getCreateTime().getTime());
				t.setStartTimeFormat(t.getStartTime().getTime());
				t.setEndTimeFormat(t.getEndTime().getTime());

				// 查询完成人数
				Map<String, Object> map1 = new HashMap<>();
				Integer allNum = 0;
				Integer answerNum = 0;
				// 回答人数
				t.getClassRelationId();
				// 根据绑定课程查询班级
				ClassRelationExample classRelationExample = new ClassRelationExample();
				classRelationExample.createCriteria().andIdEqualTo(t.getClassRelationId()).andDelStateEqualTo(1);
				List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
				Integer grades = classRelationList.get(0).getGrades();
				// 查询全部人数
				MemberExample memberExample = new MemberExample();
				com.sjzc.kt.entity.MemberExample.Criteria createCriteria = memberExample.createCriteria();
				// Criteria criteria = memberExample.createCriteria();
				createCriteria.andGradesEqualTo(grades).andDelStateEqualTo(1);
				List<Member> memberList = memberMapper.selectByExample(memberExample);
				allNum = memberList.size();
				// 查询已回答人数
				TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
				taskAnswerExample.createCriteria().andTaskIdEqualTo(t.getTaskId()).andDelStateEqualTo(1);
				List<TaskAnswer> taskAnswerList = taskAnswerMapper.selectByExample(taskAnswerExample);
				List<Integer> memberIds = new ArrayList<Integer>();
				memberIds.add(0);

				if (taskAnswerList.size() > 0 && !taskAnswerList.isEmpty()) {
					memberIds.clear();
					for (TaskAnswer tA : taskAnswerList) {
						memberIds.add(tA.getMemberId());
					}
				}else {
					memberIds.clear();
				}
				answerNum = memberIds.size();
				map1.put("allNum", allNum);
				map1.put("answerNum", answerNum);
				t.setMap(map1);
			}
		}
		return TaskList;
	}

	// 学生端课后作业数据统计
	@Override
	public List<Map<String, Object>> taskStatisticsForUser(Integer memberId, Integer classRelationId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		// 查询该用户所有课后作业
		TaskExample taskExample = new TaskExample();
		taskExample.setOrderByClause("start_time desc");
		taskExample.createCriteria().andDelStateEqualTo(1).andClassRelationIdEqualTo(classRelationId);
		List<Task> taskList = taskMapper.selectByExample(taskExample);
		// 未完成
		Integer score0 = 0;
		Integer score1 = 0;
		Integer score2 = 0;
		Integer score3 = 0;
		Integer score4 = 0;
		// 未批改
		Integer score5 = 0;

		if (taskList.size() > 0 && !taskList.isEmpty()) {
			for (Task t : taskList) {
				// 查询学生课后作业成绩
				TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
				taskAnswerExample.createCriteria().andTaskIdEqualTo(t.getTaskId()).andMemberIdEqualTo(memberId);
				List<TaskAnswer> taskAnswerList = taskAnswerMapper.selectByExample(taskAnswerExample);
				if (taskAnswerList.size() > 0 && !taskAnswerList.isEmpty()) {
					TaskAnswer taskAnswer = taskAnswerMapper.selectByExample(taskAnswerExample).get(0);

					if (null == taskAnswer.getEvaluate()) {
						score5++;
					} else {
						if (taskAnswer.getEvaluate() == 1) {
							score1++;
						}
						if (taskAnswer.getEvaluate() == 2) {
							score2++;
						}
						if (taskAnswer.getEvaluate() == 3) {
							score3++;
						}
						if (taskAnswer.getEvaluate() == 4) {
							score4++;
						}
					}
				} else {
					score0++;
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
		Map<String, Object> map5 = new HashMap<>();
		map5.put("name", "未批改");
		map5.put("value", score5);
		mapList.add(map5);
		return mapList;
	}

	//教师端查看课后作业数据统计
	@Override
	public Map<String, Object> taskStatisticsForTeacher(Integer classRelationId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		List<String> taskDataList = new ArrayList<>();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		// 查询该课程所有课后作业
		TaskExample taskExample = new TaskExample();
		taskExample.setOrderByClause("start_time asc");
		taskExample.createCriteria().andDelStateEqualTo(1).andClassRelationIdEqualTo(classRelationId);
		List<Task> taskList = taskMapper.selectByExample(taskExample);

		List<Integer> score0List = new ArrayList<>();
		List<Integer> score1List = new ArrayList<>();
		List<Integer> score2List = new ArrayList<>();
		List<Integer> score3List = new ArrayList<>();
		List<Integer> score4List = new ArrayList<>();
		List<Integer> score5List = new ArrayList<>();

		if (taskList.size() > 0 && !taskList.isEmpty()) {
			for (Task t : taskList) {
				taskDataList.add(t.getTitle());
				// 未答题
				Integer score0 = 0;
				Integer score1 = 0;
				Integer score2 = 0;
				Integer score3 = 0;
				Integer score4 = 0;
				//未批改
				Integer score5 = 0;
				
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
						// 查询学生课后作业成绩
						TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
						taskAnswerExample.createCriteria().andTaskIdEqualTo(t.getTaskId()).andMemberIdEqualTo(m.getMemberId());
						List<TaskAnswer> taskAnswerList = taskAnswerMapper.selectByExample(taskAnswerExample);
						if (taskAnswerList.size() > 0 && !taskAnswerList.isEmpty()) {
							TaskAnswer taskAnswer = taskAnswerMapper.selectByExample(taskAnswerExample).get(0);

							if (null == taskAnswer.getEvaluate()) {
								score5++;
							} else {
								if (taskAnswer.getEvaluate() == 1) {
									score1++;
								}
								if (taskAnswer.getEvaluate() == 2) {
									score2++;
								}
								if (taskAnswer.getEvaluate() == 3) {
									score3++;
								}
								if (taskAnswer.getEvaluate() == 4) {
									score4++;
								}
							}
						} else {
							score0++;
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
		Map<String, Object> map5 = new HashMap<>();
		map4.put("name", "未批改");
		map4.put("data", score5List);
		mapList.add(map5);

		map.put("taskDataList", taskDataList);
		map.put("mapList", mapList);
		return map;
	

	}

	//教师端课后作业学生列表
	@Override
	public List<Map<String, Object>> memberTaskListForTeacher(Integer grades, Integer taskId, Integer state,
			List<Integer> memberIdList) {

		// 1已完成
		if (state == 1) {
			List<Map<String, Object>> memberTaskList = taskAnswerMapper.memberTaskListForTeacher(taskId);
			// 查询成绩
			if (memberTaskList.size() > 0 && !memberTaskList.isEmpty()) {
				for (Map<String, Object> m : memberTaskList) {
					// 查询用户信息
					TbstaticExample staticExample1 = new TbstaticExample();
					staticExample1.createCriteria().andIdEqualTo(Integer.valueOf(m.get("department").toString()))
							.andDelStateEqualTo(1);
					m.put("departmentFormat", staticMapper.selectByExample(staticExample1).get(0).getDescription());

					TbstaticExample staticExample2 = new TbstaticExample();
					staticExample2.createCriteria().andIdEqualTo(Integer.valueOf(m.get("major").toString()))
							.andDelStateEqualTo(1);
					m.put("majorFormat", staticMapper.selectByExample(staticExample2).get(0).getDescription());

					TbstaticExample staticExample3 = new TbstaticExample();
					staticExample3.createCriteria().andIdEqualTo(Integer.valueOf(m.get("grades").toString()))
							.andDelStateEqualTo(1);
					m.put("gradesFormat", staticMapper.selectByExample(staticExample3).get(0).getDescription());
					// 查询学生课后作业成绩
					TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
					taskAnswerExample.createCriteria().andTaskIdEqualTo(taskId).andMemberIdEqualTo(Integer.valueOf(m.get("memberId").toString()));
					List<TaskAnswer> taskAnswerList = taskAnswerMapper.selectByExample(taskAnswerExample);
					if (taskAnswerList.size() > 0 && !taskAnswerList.isEmpty()) {
						TaskAnswer taskAnswer = taskAnswerMapper.selectByExample(taskAnswerExample).get(0);
						if (null == taskAnswer.getEvaluate()) {
							//未批改
							m.put("score", 5);
						}else {
							//分数
							m.put("score", taskAnswer.getEvaluate());
							//批改时间
							m.put("evaluateTimeFormat", taskAnswer.getModifyTime().getTime());
						}
					}
					
				}
			}
			return memberTaskList;
		} else {
			//未完成
			if(state == 2) {
				List<Map<String, Object>> memberTaskList = taskAnswerMapper.memberTaskListNOForTeacher(grades,
						memberIdList);
				if (memberTaskList.size() > 0 && !memberTaskList.isEmpty()) {
					for (Map<String, Object> m : memberTaskList) {
						// 查询用户信息
						TbstaticExample staticExample1 = new TbstaticExample();
						staticExample1.createCriteria().andIdEqualTo(Integer.valueOf(m.get("department").toString()))
								.andDelStateEqualTo(1);
						m.put("departmentFormat", staticMapper.selectByExample(staticExample1).get(0).getDescription());

						TbstaticExample staticExample2 = new TbstaticExample();
						staticExample2.createCriteria().andIdEqualTo(Integer.valueOf(m.get("major").toString()))
								.andDelStateEqualTo(1);
						m.put("majorFormat", staticMapper.selectByExample(staticExample2).get(0).getDescription());

						TbstaticExample staticExample3 = new TbstaticExample();
						staticExample3.createCriteria().andIdEqualTo(Integer.valueOf(m.get("grades").toString()))
								.andDelStateEqualTo(1);
						m.put("gradesFormat", staticMapper.selectByExample(staticExample3).get(0).getDescription());
						m.put("score", 0);
					}
				}
				return memberTaskList;
			}else {
				//已完成未批改
				List<Map<String, Object>> memberTaskList = taskAnswerMapper.memberTaskListForTeacher(taskId);
				// 查询成绩
				if (memberTaskList.size() > 0 && !memberTaskList.isEmpty()) {
					for (int i = memberTaskList.size() - 1; i >= 0; i--) {
						// 查询用户信息
						TbstaticExample staticExample1 = new TbstaticExample();
						staticExample1.createCriteria().andIdEqualTo(Integer.valueOf(memberTaskList.get(i).get("department").toString()))
								.andDelStateEqualTo(1);
						memberTaskList.get(i).put("departmentFormat", staticMapper.selectByExample(staticExample1).get(0).getDescription());

						TbstaticExample staticExample2 = new TbstaticExample();
						staticExample2.createCriteria().andIdEqualTo(Integer.valueOf(memberTaskList.get(i).get("major").toString()))
								.andDelStateEqualTo(1);
						memberTaskList.get(i).put("majorFormat", staticMapper.selectByExample(staticExample2).get(0).getDescription());

						TbstaticExample staticExample3 = new TbstaticExample();
						staticExample3.createCriteria().andIdEqualTo(Integer.valueOf(memberTaskList.get(i).get("grades").toString()))
								.andDelStateEqualTo(1);
						memberTaskList.get(i).put("gradesFormat", staticMapper.selectByExample(staticExample3).get(0).getDescription());
						// 查询学生课后作业成绩
						TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
						taskAnswerExample.createCriteria().andTaskIdEqualTo(taskId).andMemberIdEqualTo(Integer.valueOf(memberTaskList.get(i).get("memberId").toString()));
						List<TaskAnswer> taskAnswerList = taskAnswerMapper.selectByExample(taskAnswerExample);
						if (taskAnswerList.size() > 0 && !taskAnswerList.isEmpty()) {
							TaskAnswer taskAnswer = taskAnswerMapper.selectByExample(taskAnswerExample).get(0);
							if (null == taskAnswer.getEvaluate()) {
								//未批改
								memberTaskList.get(i).put("score", 0);
							}else {
								memberTaskList.remove(i);
								
							}
						}
						
					}
				}
				return memberTaskList;
			}
			
		}
		

	}
	
	// 教师移动端端查看试卷详情统计
	@Override
	public Task taskStatistics(Integer taskId) {
		// TODO Auto-generated method stub
		
		TaskExample taskExample = new TaskExample();
		taskExample.createCriteria().andDelStateEqualTo(1).andTaskIdEqualTo(taskId);
		Task task = taskMapper.selectByExample(taskExample).get(0);
		// 查询分数等级人数
		Integer score1 = 0;
		Integer score2 = 0;
		Integer score3 = 0;
		Integer score4 = 0;
		// 总人数
		Integer allNum = 0;
		// 已回答人数
		Integer answerNum = 0;
		// 未回答人数
		Integer noAnswerNum = 0;
		//未批改人数
		Integer noEvaluate = 0;
		// 查询该试卷的得分情况
		task.getClassRelationId();
		List<Member> memberList = memberService.selectMemberByCRId(task.getClassRelationId());
		// 维护总人数
		allNum = memberList.size();
		if (memberList.size() > 0 && !memberList.isEmpty()) {
			for (Member m : memberList) {
				// 查询学生课后作业成绩
				TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
				taskAnswerExample.createCriteria().andTaskIdEqualTo(task.getTaskId()).andMemberIdEqualTo(m.getMemberId());
				List<TaskAnswer> taskAnswerList = taskAnswerMapper.selectByExample(taskAnswerExample);
				if (taskAnswerList.size() > 0 && !taskAnswerList.isEmpty()) {
					TaskAnswer taskAnswer = taskAnswerMapper.selectByExample(taskAnswerExample).get(0);

					if (null == taskAnswer.getEvaluate()) {
						noEvaluate++;
					} else {
						if (taskAnswer.getEvaluate() == 1) {
							score1++;
						}
						if (taskAnswer.getEvaluate() == 2) {
							score2++;
						}
						if (taskAnswer.getEvaluate() == 3) {
							score3++;
						}
						if (taskAnswer.getEvaluate() == 4) {
							score4++;
						}
					}
				} else {
					noAnswerNum++;
				}
			}
			
		}
		// 维护已回答人数
		answerNum = allNum - noAnswerNum;
		Map<String, Object> taskMap = new HashMap<>();
		taskMap.put("score1", score1);
		taskMap.put("score2", score2);
		taskMap.put("score3", score3);
		taskMap.put("score4", score4);
		taskMap.put("allNum", allNum);
		taskMap.put("noEvaluate", noEvaluate);
		taskMap.put("answerNum", answerNum);
		taskMap.put("noAnswerNum", noAnswerNum);
		task.setMap(taskMap);
		
		return task;
	}
	

}

package com.sjzc.kt.service;

import java.util.List;
import java.util.Map;

import com.sjzc.kt.entity.Task;
import com.sjzc.kt.entity.Test;

public interface TaskService {

	/**  
	 * @Title: addTask
	 * @Description: 添加课后作业
	 * @author xyl
	 * @date 2021-01-28 
	 */
	public void addTask(Task task);
	
	/**  
	 * @Title: slectTeacherTaskList
	 * @Description: 教师端查询课后作业列表
	 * @author xyl
	 * @date 2021-01-28 
	 */
	public List<Task> slectTeacherTaskList(Task task);
	
	//学生端查询已发布课后作业列表
	public List<Task> slectMemberTaskList(Map<String, Object> map);
	
	/**  
	 * @Title: selectTaskDetail
	 * @Description: 查询课后作业详情
	 * @author xyl
	 * @date 2021-01-28 
	 */
	public Task selectTaskDetail(Integer taskId);
	
	/**  
	 * @Title: delTask
	 * @Description: 删除课后作业
	 * @author xyl
	 * @date 2021-01-28 
	 */
	public void delTask(Integer taskId);
	
	/**  
	 * @Title: answerTask
	 * @Description: 学生回答课后作业
	 * @author xyl
	 * @date 2021-01-28 
	 */
	public void answerTask(Task task);
	
	//查询课后作业学生回答详情
	public Task MemberTaskDetail(Integer taskId,Integer memberId);
	
	//评价课后作业
	public void evaluateTask(Integer taskId,Integer memberId,Integer evaluate);
	
	//教师移动端查看课后作业列表
	public List<Task> TaskListForTeacher(Map<String, Object> map);
	
	//学生端课后作业数据统计
	public List<Map<String, Object>> taskStatisticsForUser(Integer memberId,Integer classRelationId);
	
	//教师端查看课后作业数据统计
	public Map<String, Object> taskStatisticsForTeacher(Integer classRelationId);
	
	//教师端课后作业学生列表
	public List<Map<String, Object>> memberTaskListForTeacher(Integer grades,Integer taskId,Integer state,List<Integer> memberIdList);
	
	//教师移动端端查看课后作业详情统计
	public Task taskStatistics(Integer taskId);
	
}

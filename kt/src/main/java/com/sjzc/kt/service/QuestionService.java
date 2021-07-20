package com.sjzc.kt.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.Question;
import com.sjzc.kt.entity.QuestionResponse;

public interface QuestionService {

	// 教师移动端端发起提问
	public void addQuestion(Question question);

	// 学生移动端端查询提问列表
	public List<Question> studentQuestionList(Map<String, Object> map);

	// 教师端关闭举手
	public void closeQuestion(Integer questionId);

	// 教师端查看举手学生列表
	public List<QuestionResponse> handMemberList(Map<String, Object> map);

	// 学生端举手
	public void hand(Integer questionId, Integer memberId);

	// 教师端给出学生举手评分
	public void score(Integer questionResponseId,Integer grade);
	
	// 教师移动端端查询提问列表
	public List<Question> teacherQuestionList(Map<String, Object> map);
	
	//教师端课堂测验数据统计
	public Map<String, Object> questionStatisticsForTeacher(Integer classRelationId);
	
	//学生端举手情况数据统计
	public List<Map<String, Object>> questionStatisticsForUser(Integer memberId,Integer classRelationId);

}

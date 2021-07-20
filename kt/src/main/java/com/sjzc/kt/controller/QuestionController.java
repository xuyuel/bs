package com.sjzc.kt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjzc.kt.entity.Question;
import com.sjzc.kt.entity.QuestionResponse;
import com.sjzc.kt.service.QuestionService;
import com.sjzc.kt.util.R;

@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	// 教师移动端端发起提问
	@PostMapping("/addQuestion")
	public R addQuestion(@RequestBody Question question) {

		questionService.addQuestion(question);
		return R.ok();
	}

	// 学生移动端端查询提问列表
	@PostMapping("/studentQuestionList")
	public R studentQuestionList(@RequestBody Map<String, Object> map) {

		List<Question> questionList = questionService.studentQuestionList(map);
		return R.ok().put("questionList", questionList);
	}
	
	//教师端查询提问列表
	@PostMapping("/teacherQuestionList")
	public R teacherQuestionList(@RequestBody Map<String, Object> map) {

		List<Question> questionList = questionService.teacherQuestionList(map);
		return R.ok().put("questionList", questionList);
	}

	// 教师端关闭举手
	@GetMapping("closeQuestion")
	public R closeQuestion(@RequestParam Integer questionId) {

		questionService.closeQuestion(questionId);
		return R.ok();
	}

	// 教师端查看举手学生列表
	@PostMapping("handMemberList")
	public R handMemberList(@RequestBody Map<String, Object> map) {

		PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()), Integer.valueOf(map.get("pageSize").toString()));
		List<QuestionResponse> handMemberList = questionService.handMemberList(map);
		PageInfo<QuestionResponse> pageInfo = new PageInfo<QuestionResponse>(handMemberList);
		return R.ok().put("handMemberList", pageInfo);
	}

	// 学生端举手
	@GetMapping("hand")
	public R hand(@RequestParam("questionId") Integer questionId,@RequestParam("memberId") Integer memberId) {

		questionService.hand(questionId, memberId);
		return R.ok();
	}
	
	//教师端给学生举手评分
	@GetMapping("score")
	public R score(@RequestParam("questionResponseId") Integer questionResponseId,@RequestParam("grade") Integer grade) {

		questionService.score(questionResponseId, grade);
		return R.ok();
	}
	
	//学生端课堂举手数据统计
	@GetMapping("questionStatisticsForUser")
	public R questionStatisticsForUser(@RequestParam("memberId") Integer memberId,
			@RequestParam("classRelationId") Integer classRelationId) {
		
		List<Map<String, Object>> list = questionService.questionStatisticsForUser(memberId, classRelationId);
		return R.ok().put("data", list);
	}
	
	// 教师端课堂举手数据统计
	@GetMapping("/questionStatisticsForTeacher")
	public R questionStatisticsForTeacher(@RequestParam("classRelationId") Integer classRelationId) {

		Map<String, Object> map = questionService.questionStatisticsForTeacher(classRelationId);
		return R.ok().put("data", map);
	}
}

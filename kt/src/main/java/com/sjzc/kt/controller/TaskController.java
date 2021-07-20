package com.sjzc.kt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjzc.kt.dao.ClassRelationMapper;
import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.dao.TaskAnswerMapper;
import com.sjzc.kt.dao.TaskMapper;
import com.sjzc.kt.dao.TbstaticMapper;
import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.ClassRelationExample;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.MemberExample;
import com.sjzc.kt.entity.MemberExample.Criteria;
import com.sjzc.kt.entity.Task;
import com.sjzc.kt.entity.TaskAnswer;
import com.sjzc.kt.entity.TaskAnswerExample;
import com.sjzc.kt.entity.TaskExample;
import com.sjzc.kt.entity.TbstaticExample;
import com.sjzc.kt.entity.Test;
import com.sjzc.kt.entity.TestExample;
import com.sjzc.kt.exception.RRException;
import com.sjzc.kt.service.CourseService;
import com.sjzc.kt.service.TaskService;
import com.sjzc.kt.util.FileTools;
import com.sjzc.kt.util.R;

@RestController
@RequestMapping("task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private ClassRelationMapper classRelationMapper;
	@Autowired
	private CourseService courseService;
	@Autowired
	private TaskAnswerMapper taskAnswerMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private TbstaticMapper staticMapper;
	/**  
	 * @Title: addTask
	 * @Description: 添加课后作业
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@PostMapping("/addTask")
	public R addTask(@RequestBody Task task) {
		
		taskService.addTask(task);
		return R.ok("添加成功");
	}
	
	/**  
	 * @Title: slectTeacherTaskList
	 * @Description: 教师端查询课后作业列表
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@PostMapping("/slectTeacherTaskList")
	public R slectTeacherTaskList(@RequestBody Task task) {
		
		
		PageHelper.startPage(task.getPageNum(), task.getPageSize());
		List<Task> taskList = taskService.slectTeacherTaskList(task);
		PageInfo<Task> pageInfo = new PageInfo<Task>(taskList);
		return R.ok().put("taskList", pageInfo);
	}
	
	//学生端查询课后作业列表
	@PostMapping("/slectMemberTaskList")
	public R slectMemberTaskList(@RequestBody Map<String, Object> map) {
		PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()),
				Integer.valueOf(map.get("pageSize").toString()));
		
		List<Task> taskList = taskService.slectMemberTaskList(map);
		PageInfo<Task> pageInfo = new PageInfo<Task>(taskList);
		return R.ok().put("taskList", pageInfo);
	}
	
	/**  
	 * @Title: selectTaskDetail
	 * @Description: 查询课后作业详情
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@GetMapping("/selectTaskDetail")
	public R selectTaskDetail(@RequestParam Integer taskId) {
		
		Task task = taskService.selectTaskDetail(taskId);
		return R.ok().put("task", task);
	}
	
	/**  
	 * @Title: delTask
	 * @Description: 删除课后作业
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@GetMapping("/delTask")
	public R delTask(@RequestParam Integer taskId) {
		
		taskService.delTask(taskId);
		return R.ok("删除成功");
	}
	
	/**  
	 * @Title: answerTask
	 * @Description: 学生回答课后作业
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@PostMapping("/answerTask")
	public R answerTask(@RequestBody Task task) {
		
		taskService.answerTask(task);
		return R.ok("提交成功");
	}
	//回答课后作业用户列表
	@PostMapping("/memberListByTask")
	public R memberListByTask(@RequestBody Map<String, Object> map1) {
		// 查询该作业全部学生列表
		// 根据作业id查询绑定课程id
		TaskExample taskExample = new TaskExample();
		taskExample.createCriteria().andTaskIdEqualTo(Integer.valueOf(map1.get("taskId").toString()))
				.andDelStateEqualTo(1);

		// 根据绑定课程查询班级
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria()
				.andIdEqualTo(taskMapper.selectByExample(taskExample).get(0).getClassRelationId())
				.andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		Integer grades = classRelationList.get(0).getGrades();
		// 已完成作业 学生id
		TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
		taskAnswerExample.createCriteria().andTaskIdEqualTo(Integer.valueOf(map1.get("taskId").toString()))
				.andDelStateEqualTo(1);
		List<TaskAnswer> taskAnswerList = taskAnswerMapper.selectByExample(taskAnswerExample);
		List<Integer> memberIds = new ArrayList<Integer>();
		memberIds.add(0);
		if (taskAnswerList.size() > 0 && !taskAnswerList.isEmpty()) {
			for (TaskAnswer a : taskAnswerList) {
				memberIds.add(a.getMemberId());
			}
		}

		MemberExample memberExample = new MemberExample();
		Criteria criteria = memberExample.createCriteria();
		criteria.andGradesEqualTo(grades).andDelStateEqualTo(1);
		if (classRelationList.size() == 0) {
			throw new RRException("该课程不存在");
		}
		map1.put("grades", grades);
		if (map1.containsKey("state")) {
			// 1已完成
			if ("1".equals(map1.get("state").toString())) {
				criteria.andMemberIdIn(memberIds);
			}
			// 2未完成
			if ("2".equals(map1.get("state").toString())) {
				criteria.andMemberIdNotIn(memberIds);
			}
		}
		
		PageHelper.startPage(Integer.valueOf(map1.get("pageNum").toString()),
				Integer.valueOf(map1.get("pageSize").toString()));
		List<Member> memberList = memberMapper.selectByExample(memberExample);
		//Member m = new Member();
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
				
				TaskAnswerExample taskAnswerExample1 = new TaskAnswerExample();
				taskAnswerExample1.createCriteria().andTaskIdEqualTo(Integer.valueOf(map1.get("taskId").toString()))
						.andDelStateEqualTo(1).andMemberIdEqualTo(m.getMemberId());
				List<TaskAnswer> taskAnswerList1 = taskAnswerMapper.selectByExample(taskAnswerExample);
				if (taskAnswerList1.size()>0 && !taskAnswerList1.isEmpty()) {
					if (taskAnswerList1.get(0).getEvaluate()!=null) {
						map.put("evaluate", taskAnswerList1.get(0).getEvaluate());
					}else {
						map.put("evaluate", "");
					}
				}else {
					map.put("evaluate", "");
				}
				m.setMap(map);
			}
			
		}
		PageInfo<Member> pageInfo = new PageInfo<Member>(memberList);
		return R.ok().put("memberList", pageInfo);

	}
	//查询课后作业学生回答详情
	@GetMapping("/MemberTaskDetail")
	public R MemberTaskDetail(@RequestParam Integer taskId,@RequestParam Integer memberId) {
		
		Task memberTaskDetail = taskService.MemberTaskDetail(taskId, memberId);
		return R.ok().put("memberTaskDetail", memberTaskDetail);
	}
	//批改作业
	@GetMapping("/evaluateTask")
	public R evaluateTask(@RequestParam Integer taskId,@RequestParam Integer memberId, @RequestParam Integer evaluate ) {
		
		taskService.evaluateTask(taskId, memberId, evaluate);
		return R.ok("批改成功");
	}
	//作业未批改用户列表
	@PostMapping("userListForEvaluateTask")
	public R userListForEvaluateTask(@RequestBody Map<String, Object> map1) {
		//
		// 已完成作业未批改 学生id
		TaskAnswerExample taskAnswerExample = new TaskAnswerExample();
		taskAnswerExample.createCriteria().andTaskIdEqualTo(Integer.valueOf(map1.get("taskId").toString()))
				.andDelStateEqualTo(1).andEvaluateIsNull();
		List<TaskAnswer> taskAnswerList = taskAnswerMapper.selectByExample(taskAnswerExample);
		List<Integer> memberIds = new ArrayList<Integer>();
		memberIds.add(0);
		if (taskAnswerList.size() > 0 && !taskAnswerList.isEmpty()) {
			for (TaskAnswer a : taskAnswerList) {
				memberIds.add(a.getMemberId());
			}
		}
		
		MemberExample memberExample = new MemberExample();
		memberExample.createCriteria().andMemberIdIn(memberIds).andDelStateEqualTo(1);
		PageHelper.startPage(Integer.valueOf(map1.get("pageNum").toString()),
				Integer.valueOf(map1.get("pageSize").toString()));
		List<Member> memberList = memberMapper.selectByExample(memberExample);
		PageInfo<Member> pageInfo = new PageInfo<Member>(memberList);
		return R.ok().put("memberList", pageInfo);
	}
	
	//教师移动端查询课后作业列表
	@PostMapping("TaskListForTeacher")
	public R TaskListForTeacher(@RequestBody Map<String, Object> map) {
		
		List<Task> taskList = taskService.TaskListForTeacher(map);
		return R.ok().put("taskList", taskList);
	}
	
	//图片上传
	@PostMapping("/uploadFile")
	public R updateWarePic2(@RequestParam MultipartFile ufile) throws Exception {
		return R.ok().put("url", FileTools.uploadFile2(ufile));
	}
	
	// 学生端课后作业数据统计
	@GetMapping("/taskStatisticsForUser")
	public R taskStatisticsForUser(@RequestParam("memberId") Integer memberId,
			@RequestParam("classRelationId") Integer classRelationId) {

		List<Map<String, Object>> list = taskService.taskStatisticsForUser(memberId, classRelationId);
		return R.ok().put("data", list);
	}
	
	//教师端课后作业数据统计
	@GetMapping("/taskStatisticsForTeacher")
	public R taskStatisticsForTeacher(@RequestParam("classRelationId") Integer classRelationId) {

		Map<String, Object> map = taskService.taskStatisticsForTeacher(classRelationId);
		return R.ok().put("data", map);
	}
	
	// 教师端根据课后作业id查询学生完成列表
	@PostMapping("/memberTaskListForTeacher")
	public R memberTaskListForTeacher(@RequestBody Map<String, Object> map) {

		Integer state = Integer.valueOf(map.get("state").toString());
		Integer taskId = Integer.valueOf(map.get("taskId").toString());

		// 根据作业id查询绑定课程id
		TaskExample taskExample = new TaskExample();
		taskExample.createCriteria().andTaskIdEqualTo(taskId).andDelStateEqualTo(1);

		// 根据绑定课程查询班级
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria()
				.andIdEqualTo(taskMapper.selectByExample(taskExample).get(0).getClassRelationId())
				.andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		Integer grades = classRelationList.get(0).getGrades();

		// 查询已完成用户id
		List<Integer> memberIds = taskAnswerMapper.doneMember(taskId);
		memberIds.add(0);
		PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()),
				Integer.valueOf(map.get("pageSize").toString()));
		List<Map<String, Object>> memberList = taskService.memberTaskListForTeacher(grades, taskId, state, memberIds);

		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(memberList);
		return R.ok().put("memberList", pageInfo);
	}
	
	// 教师移动端端查看试卷详情统计
	@GetMapping("/taskStatistics")
	public R testStatistics(Integer taskId) {
		Task task = taskService.taskStatistics(taskId);
		return R.ok().put("task", task);
	}
}

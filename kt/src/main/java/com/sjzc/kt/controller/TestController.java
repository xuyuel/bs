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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjzc.kt.dao.ClassRelationMapper;
import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.dao.TestAnswerMapper;
import com.sjzc.kt.dao.TestMapper;
import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.ClassRelationExample;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.MemberExample;
import com.sjzc.kt.entity.TaskAnswer;
import com.sjzc.kt.entity.TaskAnswerExample;
import com.sjzc.kt.entity.TbstaticExample;
import com.sjzc.kt.entity.Test;
import com.sjzc.kt.entity.TestAnswer;
import com.sjzc.kt.entity.TestAnswerExample;
import com.sjzc.kt.entity.TestExample;
import com.sjzc.kt.entity.MemberExample.Criteria;
import com.sjzc.kt.exception.RRException;
import com.sjzc.kt.service.CourseService;
import com.sjzc.kt.service.TestService;
import com.sjzc.kt.util.R;

@RestController
@RequestMapping("test")
public class TestController {

	@Autowired
	private TestService testService;
	@Autowired
	private TestMapper testMapper;
	@Autowired
	private ClassRelationMapper classRelationMapper;
	@Autowired
	private CourseService courseService;
	@Autowired
	private TestAnswerMapper testAnswerMapper;
	@Autowired
	private MemberMapper memberMapper;
	
	/**  
	 * @Title: addTest
	 * @Description: 添加测试试卷
	 * @author xyl
	 * @date 2021-01-26 
	 */
	@PostMapping("/addTest")
	public R addTest(@RequestBody Test test) {
		//Integer teacherId = 4;
		test.setTeacherId(test.getTeacherId());
		testService.addTest(test);
		return R.ok("添加成功");
	}
	
	/**  
	 * @Title: selectTeacherTestList
	 * @Description: 教师后台管理端查询测试试卷列表 / 教师移动端查看课堂测验列表
	 * @author xyl
	 * @date 2021-01-27 
	 */
	@PostMapping("/selectTeacherTestList")
	public R selectTeacherTestList(@RequestBody Test test) {
		
		PageHelper.startPage(test.getPageNum(), test.getPageSize());
		List<Test> testList = testService.selectTeacherTestList(test);
		PageInfo<Test> pageInfo = new PageInfo<Test>(testList);
		return R.ok().put("testList", pageInfo);
	}
	/**  
	 * @Title: selectTestDetail
	 * @Description: 教师后台管理查询试卷详情/教师移动端查看试卷详情
	 * @author xyl
	 * @date 2021-01-27 
	 */
	@GetMapping("/selectTestDetail")
	public R selectTestDetail(@RequestParam("testId") Integer testId) {
		
		Test test = testService.selectTestDetail(testId);
		return R.ok().put("test", test);
	}
	
	/**  
	 * @Title: selectMemberTestList
	 * @Description: 学生端查询已发布测试试卷列表  弃用
	 * @author xyl
	 * @date 2021-01-27 
	 */
	@GetMapping("/selectMemberTestList")
	public R selectMemberTestList() {
		List<Test> testList = testService.selectMemberTestList();
		return R.ok().put("testList", testList);
	}
	//学生移动端回答测试
	@PostMapping("/answerTest")
	public R answerTest(@RequestBody Test test) {
		testService.answerTest(test);
		return R.ok("提交成功");
	}
	
	//教师移动端发布试卷
	@GetMapping("/releaseTest")
	public R releaseTest(Integer testId) {
		testService.releaseTest(testId);
		return R.ok("发布成功");
	}
	
	/**  
	 * @Title: delTest
	 * @Description: 删除试卷
	 * @author xyl
	 * @date 2021-01-28 
	 */
	@GetMapping("/delTest")
	public R delTest(Integer testId) {
		testService.delTest(testId);
		return R.ok();
	}
	
	//查询学生试卷详情
	@GetMapping("/testUserDetail")
	public R testUserDetail(@RequestParam("testId") Integer testId,@RequestParam("memberId") Integer memberId) {
		
		Test userDetail = testService.testUserDetail(testId, memberId);
		return R.ok().put("userDetail", userDetail);
	}
	
	// 教师后台管理-根据测验id查询学生列表
	@PostMapping("/memberListByTestId")
	public R memberListByTestId(@RequestBody Map<String, Object> map) {

		// 根据试卷id查询绑定课程id
		TestExample testExample = new TestExample();
		testExample.createCriteria().andTestIdEqualTo(Integer.valueOf(map.get("testId").toString()))
				.andDelStateEqualTo(1);
		// 根据绑定课程查询班级
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria()
				.andIdEqualTo(testMapper.selectByExample(testExample).get(0).getClassRelationId())
				.andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		Integer grades = classRelationList.get(0).getGrades();
		
		//条件查询-是否完成 1完成2未完成
		// 已完成测验 学生id
		TestAnswerExample testAnswerExample = new TestAnswerExample();
		testAnswerExample.createCriteria().andTestIdEqualTo(Integer.valueOf(map.get("testId").toString()))
				.andDelStateEqualTo(1);
		List<TestAnswer> testAnswerList = testAnswerMapper.selectByExample(testAnswerExample);
		List<Integer> memberIds = new ArrayList<Integer>();
		memberIds.add(0);
		if (testAnswerList.size() > 0 && !testAnswerList.isEmpty()) {
			memberIds.clear();
			for (TestAnswer t : testAnswerList) {
				if (!memberIds.contains(t.getMemberId())) {
					memberIds.add(t.getMemberId());
				}
			}
		}

		MemberExample memberExample = new MemberExample();
		Criteria criteria = memberExample.createCriteria();
		criteria.andGradesEqualTo(grades).andDelStateEqualTo(1);
		if (map.containsKey("state")) {
			if ("1".equals(map.get("state").toString())) {
				criteria.andMemberIdIn(memberIds);
			}
			if ("2".equals(map.get("state").toString())) {
				criteria.andMemberIdNotIn(memberIds);
			}
		}
		if (classRelationList.size() == 0) {
			throw new RRException("该课程不存在");
		}
		map.put("grades", grades);
		
		// 查询学生列表
		PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()),
				Integer.valueOf(map.get("pageSize").toString()));
		List<Member> memberList = memberMapper.selectByExample(memberExample);
		if (memberList.size()>0 && !memberList.isEmpty()) {
			for (Member m:memberList) {
				Map<String, Object> answerMap = new HashMap<String, Object>();
				//查询试卷回答状态
				Integer score = testService.selectTestScore(m.getMemberId(), Integer.valueOf(map.get("testId").toString()));
				answerMap.put("score", score);
				m.setMap(answerMap);
			}
			
		}
		PageInfo<Member> pageInfo = new PageInfo<Member>(memberList);
		return R.ok().put("memberList", pageInfo);
	}
	
	//移动端用户查询自己的课堂测试列表
	@PostMapping("memberTestList")
	public R TestListForMember (@RequestBody Map<String, Object> map) {
		PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()),
				Integer.valueOf(map.get("pageSize").toString()));
		List<Test> testList = testService.TestListForMember(map);
		PageInfo<Test> pageInfo = new PageInfo<Test>(testList);
		return R.ok().put("testList", pageInfo);
	}
	
	//教师移动端端查看试卷详情统计
	@GetMapping("/testStatistics")
	public R testStatistics(Integer testId) {
		Test test = testService.testStatistics(testId);
		return R.ok().put("test", test);
	}
	
	// 学生端查询学生试卷详情
	@GetMapping("/testDetailForUser")
	public R testDetailForUser(@RequestParam("testId") Integer testId, @RequestParam("memberId") Integer memberId) {

		Test userDetail = testService.testDetailForUser(testId, memberId);
		return R.ok().put("userDetail", userDetail);
	}
	
	//学生端课堂测验数据统计
	@GetMapping("/testStatisticsForUser")
	public R testStatisticsForUser(@RequestParam("memberId") Integer memberId,@RequestParam("classRelationId") Integer classRelationId) {
		
		List<Map<String, Object>> list = testService.testStatisticsForUser(memberId, classRelationId);
		return R.ok().put("data", list);
	}
	
	//教师端课堂测验数据统计
	@GetMapping("/testStatisticsForTeacher")
	public R testStatisticsForTeacher(@RequestParam("classRelationId") Integer classRelationId) {
		
		Map<String, Object> map = testService.testStatisticsForTeacher(classRelationId);
		return R.ok().put("data", map);
	}
	
	//教师端根据测验id查询学生完成列表
	@PostMapping("/memberTestListForTeacher")
	public R memberTestListForTeacher(@RequestBody Map<String, Object> map) {
		
		Integer state = Integer.valueOf(map.get("state").toString());
		Integer testId = Integer.valueOf(map.get("testId").toString());
		
		// 根据试卷id查询绑定课程id
		TestExample testExample = new TestExample();
		testExample.createCriteria().andTestIdEqualTo(Integer.valueOf(map.get("testId").toString()))
				.andDelStateEqualTo(1);
		// 根据绑定课程查询班级
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria()
				.andIdEqualTo(testMapper.selectByExample(testExample).get(0).getClassRelationId())
				.andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		Integer grades = classRelationList.get(0).getGrades();
		
		// 查询已完成用户id
		List<Integer> memberIds = testAnswerMapper.doneMember(testId);
		memberIds.add(0);
		PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()),
				Integer.valueOf(map.get("pageSize").toString()));
		List<Map<String, Object>> memberList = testService.memberTestListForTeacher(grades,testId, state,
				memberIds);

		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(memberList);
		return R.ok().put("memberList", pageInfo);
	}
	
}

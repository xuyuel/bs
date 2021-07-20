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
import com.sjzc.kt.dao.ClassRelationMapper;
import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.ClassRelationExample;
import com.sjzc.kt.entity.ClassSchedule;
import com.sjzc.kt.entity.Course;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.exception.RRException;
import com.sjzc.kt.service.CourseService;
import com.sjzc.kt.util.R;

@RestController
@RequestMapping("course")
public class CourseController {

	@Autowired
	private CourseService courseService;
	@Autowired
	private ClassRelationMapper classRelationMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	/**  
	 * @Title: addCourse
	 * @Description: 添加课程
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@PostMapping("/addCourse")
	public R addCourse(@RequestBody Course course) {
		courseService.addCourse(course);
		return R.ok("添加成功");
	}
	/**  
	 * @Title: selectCourse
	 * @Description: 查询课程列表
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@GetMapping("/selectCourse")
	public R selectCourse(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Course> courseList = courseService.selectCourse();
		PageInfo<Course> pageInfo = new PageInfo<Course>(courseList);
		return R.ok().put("courseList", pageInfo);
	}
	/**  
	 * @Title: delCourse
	 * @Description: 删除课程
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@GetMapping("delCourse")
	public R delCourse(@RequestParam Integer id) {
		courseService.delCourse(id);
		return R.ok("删除成功");
	}
	/**  
	 * @Title: addClassSchedule
	 * @Description: 添加课程安排 弃用
	 * @author xyl
	 * @date 2021-02-19 
	 */
	@GetMapping("/addClassSchedule")
	public R addClassSchedule(@RequestBody ClassSchedule classSchedule) {
		
		courseService.addClassSchedule(classSchedule);
		return R.ok("添加成功");
	}
	
	//管理员绑定课程到班级
	@PostMapping("/addCourseToClass")
	public R addCourseToClass(@RequestBody ClassRelation classRelation) {
		
		courseService.addCourseToClass(classRelation);
		return R.ok("添加成功");
	}
	
	//管理员查看班级课程列表 条件查询-课程名称-具体到班级
	@PostMapping("/selectClassRelation")
	public R selectClassRelation(@RequestBody ClassRelation classRelation) {
		PageHelper.startPage(classRelation.getPageNum(), classRelation.getPageSize());
		List<ClassRelation> ClassRelationList = courseService.selectClassRelation(classRelation);
		PageInfo<ClassRelation> pageInfo = new PageInfo<ClassRelation>(ClassRelationList);
		return R.ok().put("ClassRelationList", pageInfo);
	}
	
	//管理员删除绑定到班级的课程
	@GetMapping("delClassRelation")
	public R delClassRelation(@RequestParam Integer id) {
		courseService.delClassRelation(id);
		return R.ok("删除成功");
	}
	//教师后台管理-根据绑定的课程id查询学生列表
	@PostMapping("/memberListByClassRId")
	public R memberListByClassRId(@RequestBody Map<String, Object> map) {
		
		// 根据绑定课程id查询班级
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria().andIdEqualTo(Integer.valueOf(map.get("classRelationId").toString()))
				.andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		Integer grades = classRelationList.get(0).getGrades();
		if (classRelationList.size() == 0) {
			throw new RRException("该课程不存在");
		}
		map.put("grades", grades);
		//查询学生列表
		PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()), Integer.valueOf(map.get("pageSize").toString()));
		List<Member> memberList = courseService.memberListByClassRId(map);
		PageInfo<Member> pageInfo = new PageInfo<Member>(memberList);
		return R.ok().put("memberList", pageInfo);
	}
	
	//移动端根据用户id查询课程列表
	@GetMapping("/memberCourseList")
	public R memberCourseList(@RequestParam("memberId") Integer memberId,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize) {
		
		Member member = memberMapper.selectMemberById(memberId);
		ClassRelation classRelation = new ClassRelation();
		classRelation.setGrades(member.getGrades());
		PageHelper.startPage(pageNum, pageSize);
		List<ClassRelation> memberCourseList = courseService.memberCourseList(classRelation);
		PageInfo<ClassRelation> pageInfo = new PageInfo<ClassRelation>(memberCourseList);
		return R.ok().put("memberCourseList", pageInfo);
	}
}

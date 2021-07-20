package com.sjzc.kt.controller;

import java.text.DateFormat;
import java.util.Date;
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
import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.dao.TeacherMapper;
import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.MemberExample;
import com.sjzc.kt.entity.Teacher;
import com.sjzc.kt.entity.TeacherExample;
import com.sjzc.kt.exception.RRException;
import com.sjzc.kt.service.CourseService;
import com.sjzc.kt.service.LoginService;
import com.sjzc.kt.util.R;

@RestController
@RequestMapping("teacher")
public class TeacherController {
	
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private LoginService loginService;
	@Autowired
	private CourseService courseService;

	/**  
	 * @Title: selectTeacher
	 * @Description: 查询教师信息
	 * @author xyl
	 * @date 2021-01-21 
	 */
	@GetMapping("/selectTeacher")
	public R selectTeacher(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Teacher teacher = new Teacher();
		teacher.setDelState(2);
		TeacherExample teacherExample = new TeacherExample();
		teacherExample.createCriteria().andDelStateEqualTo(1);
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		if (teacherList.size()>0 && !teacherList.isEmpty()) {
			for (Teacher t:teacherList) {
				//格式化时间戳
				t.setCreateTimeFormat(t.getCreateTime().getTime());
				//查询该教师信息是否被绑定
				if (t.getMemberId()!=null) {
					//被绑定 查询教师用户信息
					MemberExample memberExample = new MemberExample();
					memberExample.createCriteria().andMemberIdEqualTo(t.getMemberId()).andDelStateEqualTo(1);
					t.setMember(memberMapper.selectByExample(memberExample).get(0));
				}
			}
		}
		PageInfo<Teacher> pageInfo = new PageInfo<Teacher>(teacherList);
		return R.ok().put("teacherList", pageInfo);
	}
	/**  
	 * @Title: addTeacher
	 * @Description:添加教师信息
	 * @author xyl
	 * @date 2021-01-21 
	 */
	/*@PostMapping("/addTeacher")
	public R addTeacher(@RequestBody Teacher teacher) {
		teacher.setCreateTime(new Date());
		teacher.setDelState(1);
		teacherMapper.insertSelective(teacher);
		return R.ok("添加成功");
	}*/
	/**  
	 * @Title: delTeacher
	 * @Description: 删除教师信息
	 * @author xyl
	 * @date 2021-01-21
	 */
	/*@GetMapping("/delTeacher")
	public R delTeacher(@RequestParam Integer id) {
		Teacher teacher = new Teacher();
		teacher.setDelState(2);
		TeacherExample teacherExample = new TeacherExample();
		teacherExample.createCriteria().andIdEqualTo(id);
		teacherMapper.updateByExampleSelective(teacher, teacherExample);
		return R.ok("删除成功");
	}*/
	//后台管理添加教师信息
	@PostMapping("/addTeacher")
	public R addTeacher(@RequestBody Map<String, Object> user) {
		
		/*teacher.setCreateTime(new Date());
		teacher.setDelState(1);
		teacherMapper.insertSelective(teacher);*/
		loginService.addTeacher(user);
		return R.ok("添加成功");
	}
	//删除教师信息
	@GetMapping("/delTeacher")
	public R delTeacher(@RequestParam Integer id) {
		
		//查询该教师是否被添加课程
		ClassRelation classRelation = new ClassRelation();
		classRelation.setTeacherId(id);
		List<ClassRelation> selectClassRelation = courseService.selectClassRelation(classRelation);
		if (selectClassRelation.size()!=0) {
			throw new RRException("该教师已绑定课程，不允许删除");
		}
		
		Member member = new Member();
		member.setDelState(2);
		MemberExample memberExample = new MemberExample();
		memberExample.createCriteria().andMemberIdEqualTo(id);
		
		memberMapper.updateByExampleSelective(member, memberExample);
		return R.ok("删除成功");
	}
	
}

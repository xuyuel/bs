package com.sjzc.kt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjzc.kt.dao.ClassRelationMapper;
import com.sjzc.kt.dao.ClassScheduleMapper;
import com.sjzc.kt.dao.CourseMapper;
import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.dao.TbstaticMapper;
import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.ClassRelationExample;
import com.sjzc.kt.entity.ClassRelationExample.Criteria;
import com.sjzc.kt.entity.ClassSchedule;
import com.sjzc.kt.entity.Course;
import com.sjzc.kt.entity.CourseExample;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.TbstaticExample;
import com.sjzc.kt.exception.RRException;
import com.sjzc.kt.service.CourseService;
import com.sjzc.kt.service.MemberService;
@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private ClassRelationMapper classRelationMapper;
	@Autowired
	private ClassScheduleMapper classScheduleMapper;
	@Autowired 
	private MemberMapper memberMapper;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TbstaticMapper staticMapper;
	/**  
	 * @Title: addCourse
	 * @Description: 添加课程
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public void addCourse(Course course) {
		// TODO Auto-generated method stub
		course.setDelState(1);
		course.setCreateTime(new Date());
		courseMapper.insertSelective(course);
	}

	/**  
	 * @Title: selectCourse
	 * @Description: 查询课程列表
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public List<Course> selectCourse() {
		// TODO Auto-generated method stub
		CourseExample courseExample = new CourseExample();
		courseExample.createCriteria().andDelStateEqualTo(1);
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		if (courseList.size()>0 && !courseList.isEmpty()) {
			for (Course c:courseList) {
				c.setCreateTimeFormat(c.getCreateTime().getTime());
			}
		}
		return courseList;
	}

	/**  
	 * @Title: delCourse
	 * @Description: 删除课程
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public void delCourse(Integer id) {
		// TODO Auto-generated method stub
		Course course = new Course();
		course.setDelState(2);
		CourseExample courseExample = new CourseExample();
		courseExample.createCriteria().andCourseIdEqualTo(id);
		courseMapper.updateByExampleSelective(course, courseExample);
	}

	/**  
	 * @Title: addClassSchedule
	 * @Description: 添加课程安排
	 * @author xyl
	 * @date 2021-02-19 
	 */
	@Override
	public void addClassSchedule(ClassSchedule classSchedule) {
		// TODO Auto-generated method stub
		//查询课程关联表是否已经添加课程关联数据
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria().andCourseIdEqualTo(classSchedule.getCourseId()).andDepartmentEqualTo(classSchedule.getDepartment())
		.andMajorEqualTo(classSchedule.getMajor()).andGradesEqualTo(classSchedule.getGrades()).andTeacherIdEqualTo(classSchedule.getTeacherId())
		.andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		//如果没有插入课程关联数据，插入课程关联数据
		if (classRelationList.size()==0) {
			ClassRelation classRelation = new ClassRelation();
			classRelation.setCourseId(classSchedule.getCourseId());
			classRelation.setCourseName(classSchedule.getCourseName());
			classRelation.setCreateTime(new Date());
			classRelation.setDelState(1);
			classRelation.setDepartment(classSchedule.getDepartment());
			classRelation.setGrades(classSchedule.getGrades());
			classRelation.setMajor(classSchedule.getMajor());
			classRelation.setTeacherId(classSchedule.getTeacherId());
			classRelationMapper.insertSelective(classRelation);
			classSchedule.setClassRelationId(classRelation.getId());
		}
		classSchedule.setClassRelationId(classRelationList.get(0).getId());
		//添加课程安排数据
		classSchedule.setCreateTime(new Date());
		classSchedule.setDelState(1);
		classScheduleMapper.insertSelective(classSchedule);
		
	}

	//管理员绑定课程到班级
	@Override
	public void addCourseToClass(ClassRelation classRelation) {
		// TODO Auto-generated method stub
		//查询该班级是否添加该课程
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria().andCourseIdEqualTo(classRelation.getCourseId()).andGradesEqualTo(classRelation.getGrades())
		.andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		if (classRelationList.size()!=0) {
			throw new RRException("该班级已添加该课程");
		}
		classRelation.setCreateTime(new Date());
		classRelation.setDelState(1);
		classRelationMapper.insertSelective(classRelation);
		
	}

	//管理员查看班级课程列表 条件查询
	@Override
	public List<ClassRelation> selectClassRelation(ClassRelation classRelation) {
		// TODO Auto-generated method stub
		
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.setOrderByClause("create_time desc");
		Criteria criteria = classRelationExample.createCriteria();
		if (classRelation.getTeacherId()!=null) {
			criteria.andTeacherIdEqualTo(classRelation.getTeacherId());
		}
		//课程名称条件查询
		if (classRelation.getCourseName()!=null) {
			criteria.andCourseNameLike("%"+classRelation.getCourseName()+"%");
		}
		//班级条件查询
		if (classRelation.getGrades()!=null) {
			criteria.andGradesEqualTo(classRelation.getGrades());
		}
		//
		criteria.andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		//查询教师姓名
		if(classRelationList.size()>0 && !classRelationList.isEmpty()) {
			for (ClassRelation c:classRelationList) {
				c.setCreateTimeFormat(c.getCreateTime().getTime());
				Member member = memberMapper.selectMemberById(c.getTeacherId());
				c.setTeacherName(member.getName());
				//查询专业
				Map<String, Object> map = new HashMap<String, Object>();
				
				TbstaticExample staticExample3 = new TbstaticExample();
				staticExample3.createCriteria().andIdEqualTo(c.getGrades()).andDelStateEqualTo(1);
				map.put("grades", staticMapper.selectByExample(staticExample3).get(0).getDescription());
				staticMapper.selectByExample(staticExample3).get(0).getParent();
				
				TbstaticExample staticExample2 = new TbstaticExample();
				staticExample2.createCriteria().andIdEqualTo(staticMapper.selectByExample(staticExample3).get(0).getParent()).andDelStateEqualTo(1);
				map.put("majormajor", staticMapper.selectByExample(staticExample2).get(0).getDescription());
				
				TbstaticExample staticExample1 = new TbstaticExample();
				staticExample1.createCriteria().andIdEqualTo(staticMapper.selectByExample(staticExample2).get(0).getParent()).andDelStateEqualTo(1);
				map.put("department", staticMapper.selectByExample(staticExample1).get(0).getDescription());
				
				c.setMap(map);
			}
			
		}
		return classRelationList;
	}

	@Override
	public void delClassRelation(Integer id) {
		// TODO Auto-generated method stub
		//删除前校验是否能够删除
		ClassRelation classRelation = new ClassRelation();
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria().andIdEqualTo(id);
		
		classRelation.setDelState(2);
		
		classRelationMapper.updateByExampleSelective(classRelation, classRelationExample);
				
	}

	//后台管理教师端根据绑定到班级的课程id查询学生列表
	@Override
	public List<Member> memberListByClassRId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		/*//根据绑定课程id查询班级
		ClassRelationExample classRelationExample = new ClassRelationExample();
		classRelationExample.createCriteria().andIdEqualTo(Integer.valueOf(map.get("classRelationId").toString())).andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		Integer grades = classRelationList.get(0).getGrades();
		if (classRelationList.size()==0) {
			throw new RRException("该课程不存在");
		}*/
		
		//map.get("classRelationId");
		Member member = new Member();
		//姓名条件查询
		if (map.containsKey("name")){
			member.setName(map.get("name").toString());
		}
		member.setGrades(Integer.valueOf(map.get("grades").toString()));
		member.setRole(1);
		List<Member> memberList = memberService.memberList(member);
		return memberList;
	}

	@Override
	public List<ClassRelation> memberCourseList(ClassRelation classRelation) {
		// TODO Auto-generated method stub
		ClassRelationExample classRelationExample = new ClassRelationExample();
		Criteria criteria = classRelationExample.createCriteria();
		//班级条件查询
		if (classRelation.getGrades()!=null) {
			criteria.andGradesEqualTo(classRelation.getGrades());
		}
		//
		criteria.andDelStateEqualTo(1);
		List<ClassRelation> classRelationList = classRelationMapper.selectByExample(classRelationExample);
		//查询教师姓名
		if(classRelationList.size()>0 && !classRelationList.isEmpty()) {
			for (ClassRelation c:classRelationList) {
				c.setCreateTimeFormat(c.getCreateTime().getTime());
				Member member = memberMapper.selectMemberById(c.getTeacherId());
				c.setTeacherName(member.getName());
				//查询专业
				Map<String, Object> map = new HashMap<String, Object>();
				
				TbstaticExample staticExample3 = new TbstaticExample();
				staticExample3.createCriteria().andIdEqualTo(c.getGrades()).andDelStateEqualTo(1);
				map.put("grades", staticMapper.selectByExample(staticExample3).get(0).getDescription());
				staticMapper.selectByExample(staticExample3).get(0).getParent();
				
				TbstaticExample staticExample2 = new TbstaticExample();
				staticExample2.createCriteria().andIdEqualTo(staticMapper.selectByExample(staticExample3).get(0).getParent()).andDelStateEqualTo(1);
				map.put("majormajor", staticMapper.selectByExample(staticExample2).get(0).getDescription());
				
				TbstaticExample staticExample1 = new TbstaticExample();
				staticExample1.createCriteria().andIdEqualTo(staticMapper.selectByExample(staticExample2).get(0).getParent()).andDelStateEqualTo(1);
				map.put("department", staticMapper.selectByExample(staticExample1).get(0).getDescription());
				
				c.setMap(map);
			}
			
		}
		return classRelationList;
	}
	

}

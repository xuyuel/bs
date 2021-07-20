package com.sjzc.kt.service;

import java.util.List;
import java.util.Map;

import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.ClassSchedule;
import com.sjzc.kt.entity.Course;
import com.sjzc.kt.entity.Member;

public interface CourseService {

	/**  
	 * @Title: addCourse
	 * @Description: 添加课程
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public void addCourse(Course course);
	
	/**  
	 * @Title: selectCourse
	 * @Description:查询课程列表
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public List<Course> selectCourse();
	
	/**  
	 * @Title: delCourse
	 * @Description: 删除课程
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public void delCourse(Integer id);
	
	/**  
	 * @Title: addClassSchedule
	 * @Description: 添加课程安排
	 * @author xyl
	 * @date 2021-02-19 
	 */
	public void addClassSchedule(ClassSchedule classSchedule);
	
	//管理员添加课程到班级
	public void addCourseToClass(ClassRelation classRelation);
	
	//管理员查看班级课程列表 条件查询
	public List<ClassRelation> selectClassRelation(ClassRelation classRelation);
	
	//管理员删除绑定到班级的课程
	public void delClassRelation(Integer id);
	
	//后台管理教师端根据绑定到班级的课程id查询学生列表
	public List<Member> memberListByClassRId(Map<String, Object> map);
	
	//移动端用户查询自己的课程列表
	public List<ClassRelation> memberCourseList(ClassRelation classRelation);
	
	
}

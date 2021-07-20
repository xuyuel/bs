package com.sjzc.kt.service;

import java.util.List;
import java.util.Map;

import com.sjzc.kt.entity.Test;

public interface TestService {

	/**  
	 * @Title: addTest
	 * @Description: 添加测试试卷
	 * @author xyl
	 * @date 2021-01-26 
	 */
	public void addTest(Test test);
	
	/**  
	 * @Title: selectTeacherTestList
	 * @Description: 教师后台管理端查询测试试卷列表
	 * @author xyl
	 * @date 2021-01-27 
	 */
	public List<Test> selectTeacherTestList(Test test);
	
	/**  
	 * @Title: selectTestDetail
	 * @Description: 查询试卷详情
	 * @author xyl
	 * @date 2021-01-27 
	 */
	public Test selectTestDetail(Integer testId);
	
	/**  
	 * @Title: selectMemberTestList
	 * @Description: TODO(描述)
	 * @author xyl
	 * @date 2021-01-27 
	 */
	public List<Test> selectMemberTestList();
	
	/**  
	 * @Title: answerTest
	 * @Description: 学生回答测试
	 * @author xyl
	 * @date 2021-01-28 
	 */
	public void answerTest(Test test);
	
	/**  
	 * @Title: releaseTest
	 * @Description: 发布试卷
	 * @author xyl
	 * @date 2021-01-28 
	 */
	public void releaseTest(Integer testId);
	
	/**  
	 * @Title: delTest
	 * @Description: 删除试卷
	 * @author xyl
	 * @date 2021-01-28 
	 */
	public void delTest(Integer testId);
	//查询用户回答试卷详情
	public Test testUserDetail(Integer testId,Integer memberId);
	
	//移动端用户查询自己的测试列表
	public List<Test> TestListForMember(Map<String, Object> map);
	
	//根据测试试卷id用户id查询试卷成绩
	public Integer selectTestScore(Integer memberId,Integer testId);
	
	//教师移动端端查看试卷详情统计
	public Test testStatistics(Integer testId);
	
	//学生端查询试卷回答详情
	public Test testDetailForUser(Integer testId,Integer memberId);
	
	//学生端课堂测验数据统计
	public List<Map<String, Object>> testStatisticsForUser(Integer memberId,Integer classRelationId);
	
	//教师端课堂测验数据统计
	public Map<String, Object> testStatisticsForTeacher(Integer classRelationId);
	
	//教师端查询测试学生列表
	public List<Map<String, Object>> memberTestListForTeacher(Integer grades,Integer testId,Integer state,List<Integer> memberIdList);
}

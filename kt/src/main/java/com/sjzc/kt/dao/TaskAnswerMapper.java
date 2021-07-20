package com.sjzc.kt.dao;

import com.sjzc.kt.entity.TaskAnswer;
import com.sjzc.kt.entity.TaskAnswerExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TaskAnswerMapper {
    int countByExample(TaskAnswerExample example);

    int deleteByExample(TaskAnswerExample example);

    int deleteByPrimaryKey(Integer answerId);

    int insert(TaskAnswer record);

    int insertSelective(TaskAnswer record);

    List<TaskAnswer> selectByExample(TaskAnswerExample example);

    TaskAnswer selectByPrimaryKey(Integer answerId);

    int updateByExampleSelective(@Param("record") TaskAnswer record, @Param("example") TaskAnswerExample example);

    int updateByExample(@Param("record") TaskAnswer record, @Param("example") TaskAnswerExample example);

    int updateByPrimaryKeySelective(TaskAnswer record);

    int updateByPrimaryKey(TaskAnswer record);
    
    //查询已回答测试用户列表
    List<Map<String, Object>> memberTaskListForTeacher(Integer taskId);
    //查询未回答测试用户列表
    List<Map<String, Object>> memberTaskListNOForTeacher(@Param("grades") Integer grades,@Param("memberIds") List<Integer> memberIds);
    
    //查询已回答测试用户列表
    List<Integer> doneMember(Integer taskId);
}
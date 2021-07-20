package com.sjzc.kt.dao;

import com.sjzc.kt.entity.TaskQuestion;
import com.sjzc.kt.entity.TaskQuestionExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TaskQuestionMapper {
    int countByExample(TaskQuestionExample example);

    int deleteByExample(TaskQuestionExample example);

    int deleteByPrimaryKey(Integer questionId);

    int insert(TaskQuestion record);

    int insertSelective(TaskQuestion record);

    List<TaskQuestion> selectByExample(TaskQuestionExample example);

    TaskQuestion selectByPrimaryKey(Integer questionId);

    int updateByExampleSelective(@Param("record") TaskQuestion record, @Param("example") TaskQuestionExample example);

    int updateByExample(@Param("record") TaskQuestion record, @Param("example") TaskQuestionExample example);

    int updateByPrimaryKeySelective(TaskQuestion record);

    int updateByPrimaryKey(TaskQuestion record);
}
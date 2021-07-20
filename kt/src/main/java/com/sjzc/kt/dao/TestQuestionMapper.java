package com.sjzc.kt.dao;

import com.sjzc.kt.entity.TestQuestion;
import com.sjzc.kt.entity.TestQuestionExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TestQuestionMapper {
    int countByExample(TestQuestionExample example);

    int deleteByExample(TestQuestionExample example);

    int deleteByPrimaryKey(Integer questionId);

    int insert(TestQuestion record);

    int insertSelective(TestQuestion record);

    List<TestQuestion> selectByExample(TestQuestionExample example);

    TestQuestion selectByPrimaryKey(Integer questionId);

    int updateByExampleSelective(@Param("record") TestQuestion record, @Param("example") TestQuestionExample example);

    int updateByExample(@Param("record") TestQuestion record, @Param("example") TestQuestionExample example);

    int updateByPrimaryKeySelective(TestQuestion record);

    int updateByPrimaryKey(TestQuestion record);
}
package com.sjzc.kt.dao;

import com.sjzc.kt.entity.QuestionResponse;
import com.sjzc.kt.entity.QuestionResponseExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface QuestionResponseMapper {
    int countByExample(QuestionResponseExample example);

    int deleteByExample(QuestionResponseExample example);

    int deleteByPrimaryKey(Integer responseId);

    int insert(QuestionResponse record);

    int insertSelective(QuestionResponse record);

    List<QuestionResponse> selectByExample(QuestionResponseExample example);

    QuestionResponse selectByPrimaryKey(Integer responseId);

    int updateByExampleSelective(@Param("record") QuestionResponse record, @Param("example") QuestionResponseExample example);

    int updateByExample(@Param("record") QuestionResponse record, @Param("example") QuestionResponseExample example);

    int updateByPrimaryKeySelective(QuestionResponse record);

    int updateByPrimaryKey(QuestionResponse record);
}
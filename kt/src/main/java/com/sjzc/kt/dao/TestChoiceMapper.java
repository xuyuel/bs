package com.sjzc.kt.dao;

import com.sjzc.kt.entity.TestChoice;
import com.sjzc.kt.entity.TestChoiceExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TestChoiceMapper {
    int countByExample(TestChoiceExample example);

    int deleteByExample(TestChoiceExample example);

    int deleteByPrimaryKey(Integer choiceId);

    int insert(TestChoice record);

    int insertSelective(TestChoice record);

    List<TestChoice> selectByExample(TestChoiceExample example);

    TestChoice selectByPrimaryKey(Integer choiceId);

    int updateByExampleSelective(@Param("record") TestChoice record, @Param("example") TestChoiceExample example);

    int updateByExample(@Param("record") TestChoice record, @Param("example") TestChoiceExample example);

    int updateByPrimaryKeySelective(TestChoice record);

    int updateByPrimaryKey(TestChoice record);
}
package com.sjzc.kt.dao;

import com.sjzc.kt.entity.TestAnswer;
import com.sjzc.kt.entity.TestAnswerExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TestAnswerMapper {
    int countByExample(TestAnswerExample example);

    int deleteByExample(TestAnswerExample example);

    int deleteByPrimaryKey(Integer answerId);

    int insert(TestAnswer record);

    int insertSelective(TestAnswer record);

    List<TestAnswer> selectByExample(TestAnswerExample example);

    TestAnswer selectByPrimaryKey(Integer answerId);

    int updateByExampleSelective(@Param("record") TestAnswer record, @Param("example") TestAnswerExample example);

    int updateByExample(@Param("record") TestAnswer record, @Param("example") TestAnswerExample example);

    int updateByPrimaryKeySelective(TestAnswer record);

    int updateByPrimaryKey(TestAnswer record);
    
    //查询已回答测试用户列表
    List<Map<String, Object>> memberTestListForTeacher(Integer testId);
    //查询未回答测试用户列表
    List<Map<String, Object>> memberTestListNOForTeacher(@Param("grades") Integer grades,@Param("memberIds") List<Integer> memberIds);
    
    //查询已回答测试用户列表
    List<Integer> doneMember(Integer testId);
    
}
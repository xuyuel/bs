package com.sjzc.kt.dao;

import com.sjzc.kt.entity.ClassSchedule;
import com.sjzc.kt.entity.ClassScheduleExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ClassScheduleMapper {
    int countByExample(ClassScheduleExample example);

    int deleteByExample(ClassScheduleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClassSchedule record);

    int insertSelective(ClassSchedule record);

    List<ClassSchedule> selectByExample(ClassScheduleExample example);

    ClassSchedule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClassSchedule record, @Param("example") ClassScheduleExample example);

    int updateByExample(@Param("record") ClassSchedule record, @Param("example") ClassScheduleExample example);

    int updateByPrimaryKeySelective(ClassSchedule record);

    int updateByPrimaryKey(ClassSchedule record);
}
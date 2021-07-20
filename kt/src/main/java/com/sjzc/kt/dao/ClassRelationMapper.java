package com.sjzc.kt.dao;

import com.sjzc.kt.entity.ClassRelation;
import com.sjzc.kt.entity.ClassRelationExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ClassRelationMapper {
    int countByExample(ClassRelationExample example);

    int deleteByExample(ClassRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClassRelation record);

    int insertSelective(ClassRelation record);

    List<ClassRelation> selectByExample(ClassRelationExample example);

    ClassRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClassRelation record, @Param("example") ClassRelationExample example);

    int updateByExample(@Param("record") ClassRelation record, @Param("example") ClassRelationExample example);

    int updateByPrimaryKeySelective(ClassRelation record);

    int updateByPrimaryKey(ClassRelation record);
}
package com.sjzc.kt.dao;

import com.sjzc.kt.entity.Tbstatic;
import com.sjzc.kt.entity.TbstaticExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TbstaticMapper {
    int countByExample(TbstaticExample example);

    int deleteByExample(TbstaticExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tbstatic record);

    int insertSelective(Tbstatic record);

    List<Tbstatic> selectByExample(TbstaticExample example);

    Tbstatic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Tbstatic record, @Param("example") TbstaticExample example);

    int updateByExample(@Param("record") Tbstatic record, @Param("example") TbstaticExample example);

    int updateByPrimaryKeySelective(Tbstatic record);

    int updateByPrimaryKey(Tbstatic record);
}
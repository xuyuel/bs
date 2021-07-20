package com.sjzc.kt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.MemberExample;

@Mapper
public interface MemberMapper {
    int countByExample(MemberExample example);

    int deleteByExample(MemberExample example);

    int deleteByPrimaryKey(Integer memberId);

    int insert(Member record);

    int insertSelective(Member record);

    List<Member> selectByExample(MemberExample example);

    Member selectByPrimaryKey(Integer memberId);

    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
    
    /**  
     * @Title: selectMemberById
     * @Description: 根据memberId查询人员信息
     * @author xyl
     * @date 2021-01-25 
     */
    public Member selectMemberById(Integer memberId);
    
    //查询openid是否存在
  	public int  quaryOpenid(String openid);
  	
  	//录入用户信息
  	public void insertMember(Member member);
  	
  	//查询memberId
  	public Map<String, Object> quaryMember(String openId);
  	
  	
}
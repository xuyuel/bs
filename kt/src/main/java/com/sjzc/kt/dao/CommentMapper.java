package com.sjzc.kt.dao;

import com.sjzc.kt.entity.Comment;
import com.sjzc.kt.entity.CommentExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CommentMapper {
    int countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
    
    /**  
     * @Title: selectCommentByParentId
     * @Description: 根据父id查询评论
     * @author xyl
     * @date 2021-01-25 
     */
    List<Comment> selectCommentByParentId(Integer parentId);
}
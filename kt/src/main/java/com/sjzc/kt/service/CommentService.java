package com.sjzc.kt.service;

import java.util.List;
import java.util.Map;

import com.sjzc.kt.entity.Comment;
import com.sjzc.kt.entity.Post;
import com.sjzc.kt.exception.RRException;

public interface CommentService {

	/**  
	 * @Title: addPost
	 * @Description: 添加问题
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public void addPost(Post post);
	
	/**  
	 * @Title: selectPostList
	 * @Description: 查询问题列表
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public List<Post> selectPostList();
	
	/**  
	 * @Title: selectPostById
	 * @Description: 查询问题详情 
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public Post selectPostById(Integer postId);
	
	/**  
	 * @Title: delPost
	 * @Description: 删除问题
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public void delPost(Integer postId);
	
	/**  
	 * @Title: addComment
	 * @Description: 添加评论
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public void addComment(Comment comment);
	
	/**  
	 * @Title: selectFirstComment
	 * @Description: 查询一级评论列表
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public List<Comment> selectComment(Integer postId);
	
	/**  
	 * @Title: selectAllComment
	 * @Description: 查询一级评论下的评论
	 * @author xyl
	 * @date 2021-01-22 
	 */
	//public List<Comment> selectAllComment(Integer commentId) throws RRException;
	
	/**  
	 * @Title: delComment
	 * @Description: 删除评论
	 * @author xyl
	 * @date 2021-01-22 
	 */
	public void delComment(Integer commentId);
	
	//查询我的问题
	public List<Post> myPost(Integer memberId);
 	
}

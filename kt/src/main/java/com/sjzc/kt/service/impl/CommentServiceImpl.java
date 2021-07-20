package com.sjzc.kt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjzc.kt.dao.CommentMapper;
import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.dao.PostMapper;
import com.sjzc.kt.dao.TbstaticMapper;
import com.sjzc.kt.entity.Comment;
import com.sjzc.kt.entity.CommentExample;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.MemberExample;
import com.sjzc.kt.entity.Post;
import com.sjzc.kt.entity.PostExample;
import com.sjzc.kt.entity.TbstaticExample;
import com.sjzc.kt.exception.RRException;
import com.sjzc.kt.service.CommentService;
import com.sjzc.kt.service.MemberService;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostMapper postMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private TbstaticMapper staticMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private MemberService memberService;
	
	/**  
	 * @Title: addPost
	 * @Description: 添加问题
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public void addPost(Post post) {
		// TODO Auto-generated method stub
		post.setCreateTime(new Date());
		post.setDelState(1);
		postMapper.insertSelective(post);
	}

	/**  
	 * @Title: selectPostList
	 * @Description: 查询问题列表
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public List<Post> selectPostList() {
		// TODO Auto-generated method stub
		PostExample postExample = new PostExample();
		postExample.setOrderByClause("create_time desc");
		postExample.createCriteria().andDelStateEqualTo(1);
		List<Post> postList = postMapper.selectByExample(postExample);
		if(postList.size()>0 && !postList.isEmpty()) {
			for (Post p:postList) {
				//评论数量
				CommentExample commentExample = new CommentExample();
				commentExample.createCriteria().andPostIdEqualTo(p.getPostId()).andDelStateEqualTo(1);
				p.setCommentNum(commentMapper.countByExample(commentExample));
				
				p.setCreateTimeFormat(p.getCreateTime().getTime());
				MemberExample memberExample = new MemberExample();
				memberExample.createCriteria().andDelStateEqualTo(1).andMemberIdEqualTo(p.getMemberId());
				List<Member> memberList = memberMapper.selectByExample(memberExample);
				Member m = new Member();
				if(memberList.size()>0 && !memberList.isEmpty()) {
					m = memberList.get(0);
					Map<String, Object> map = new HashMap<String, Object>();
					
					TbstaticExample staticExample1 = new TbstaticExample();
					staticExample1.createCriteria().andIdEqualTo(m.getDepartment()).andDelStateEqualTo(1);
					map.put("department", staticMapper.selectByExample(staticExample1).get(0).getDescription());
					
					TbstaticExample staticExample2 = new TbstaticExample();
					staticExample2.createCriteria().andIdEqualTo(m.getMajor()).andDelStateEqualTo(1);
					map.put("majormajor", staticMapper.selectByExample(staticExample2).get(0).getDescription());
					
					TbstaticExample staticExample3 = new TbstaticExample();
					staticExample3.createCriteria().andIdEqualTo(m.getGrades()).andDelStateEqualTo(1);
					map.put("grades", staticMapper.selectByExample(staticExample3).get(0).getDescription());
					m.setMap(map);
				}
				p.setMember(m);
			}
		}
		return postList;
	}

	/**  
	 * @Title: selectPostById
	 * @Description: 查询问题详情 
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public Post selectPostById(Integer postId) {
		// TODO Auto-generated method stub
		PostExample postExample = new PostExample();
		postExample.createCriteria().andDelStateEqualTo(1).andPostIdEqualTo(postId);
		Post post = postMapper.selectByExample(postExample).get(0);
		post.setCreateTimeFormat(post.getCreateTime().getTime());
		MemberExample memberExample = new MemberExample();
		memberExample.createCriteria().andDelStateEqualTo(1).andMemberIdEqualTo(post.getMemberId());
		List<Member> memberList = memberMapper.selectByExample(memberExample);
		Member m = new Member();
		if (memberList.size() > 0 && !memberList.isEmpty()) {
			m = memberList.get(0);
			Map<String, Object> map = new HashMap<String, Object>();

			TbstaticExample staticExample1 = new TbstaticExample();
			staticExample1.createCriteria().andIdEqualTo(m.getDepartment()).andDelStateEqualTo(1);
			map.put("department", staticMapper.selectByExample(staticExample1).get(0).getDescription());

			TbstaticExample staticExample2 = new TbstaticExample();
			staticExample2.createCriteria().andIdEqualTo(m.getMajor()).andDelStateEqualTo(1);
			map.put("majormajor", staticMapper.selectByExample(staticExample2).get(0).getDescription());

			TbstaticExample staticExample3 = new TbstaticExample();
			staticExample3.createCriteria().andIdEqualTo(m.getGrades()).andDelStateEqualTo(1);
			map.put("grades", staticMapper.selectByExample(staticExample3).get(0).getDescription());
			m.setMap(map);
		}
		post.setMember(m);

		return post;
	}

	/**  
	 * @Title: addComment
	 * @Description: 添加评论
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		comment.setCreateTime(new Date());
		comment.setDelState(1);
		commentMapper.insertSelective(comment);
	}

	/**  
	 * @Title: selectFirstComment
	 * @Description: 查询评论列表
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public List<Comment> selectComment(Integer postId) {
		// TODO Auto-generated method stub
		CommentExample commentExample = new CommentExample();
		commentExample.setOrderByClause("create_time desc");
		commentExample.createCriteria().andDelStateEqualTo(1).andPostIdEqualTo(postId);
		List<Comment> commentList = commentMapper.selectByExample(commentExample);
		if (commentList.size()>0 && !commentList.isEmpty()) {
			for(Comment c:commentList) {
				c.setCreateTimeFormat(c.getCreateTime().getTime());
				//查询用户信息
				Member member = memberService.selectMemberById(c.getMemberId());
				c.setMember(member);
			}
		}
		return commentList;
	}

	/**  
	 * @Title: selectAllComment
	 * @Description: 查询一级评论下的评论
	 * @author xyl
	 * @date 2021-01-22 
	 */
	/*@Override
	public List<Comment> selectAllComment(Integer commentId) throws RRException {
		// TODO Auto-generated method stub
		
		List<Comment> commentList = commentMapper.selectCommentByParentId(commentId);
		for (Comment comment:commentList) {
			List<Comment> childCommentList = commentMapper.selectCommentByParentId(comment.getCommentId());
			comment.setCommentList(childCommentList);
			//评论发布人信息
			Member member = memberMapper.selectMemberById(comment.getMemberId());
			if (member!=null) {
				comment.setMemberName(member.getName());
			}
			//评论回复人信息
			CommentExample commentExample = new CommentExample();
			commentExample.createCriteria().andDelStateEqualTo(1).andCommentIdEqualTo(comment.getParent());
			List<Comment> replyComment = commentMapper.selectByExample(commentExample);
			if (replyComment.size()==0 && replyComment.isEmpty()) {
				throw new RRException("该评论不存在");
			}
			Member replyMember = memberMapper.selectMemberById(replyComment.get(0).getMemberId());
			if (replyMember!=null) {
				comment.setReplyMemberId(replyComment.get(0).getMemberId());
				comment.setReplyMemberName(replyMember.getName());
			}
			
			if(childCommentList != null && childCommentList.size() > 0){//当前子节点有数据
                getComment(childCommentList);//子节点传过去
            }
		}
		return commentList;
	}
	public void getComment(List<Comment> childCommentList) {
		for (Comment comment:childCommentList) {
			List<Comment> childComment = commentMapper.selectCommentByParentId(comment.getCommentId());
			//评论发布人信息
			Member member = memberMapper.selectMemberById(comment.getMemberId());
			if (member!=null) {
				comment.setMemberName(member.getName());
			}
			//评论回复人信息
			CommentExample commentExample = new CommentExample();
			commentExample.createCriteria().andDelStateEqualTo(1).andCommentIdEqualTo(comment.getParent());
			List<Comment> replyComment = commentMapper.selectByExample(commentExample);
			if (replyComment.size()==0 && replyComment.isEmpty()) {
				throw new RRException("该评论不存在");
			}
			Member replyMember = memberMapper.selectMemberById(replyComment.get(0).getMemberId());
			comment.setReplyMemberId(replyComment.get(0).getMemberId());
			comment.setReplyMemberName(replyMember.getName());
			if(childComment != null && childComment.size() > 0){
                comment.setCommentList(childComment);
                getComment(childComment);//不为空递归处理
            }
		}
	}*/

	/**  
	 * @Title: delComment
	 * @Description: 删除问题
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public void delPost(Integer postId) {
		// TODO Auto-generated method stub
		PostExample postExample = new PostExample();
		postExample.createCriteria().andPostIdEqualTo(postId);
		Post post = new Post();
		post.setDelState(2);
		postMapper.updateByExampleSelective(post, postExample);
	}

	/**  
	 * @Title: delComment
	 * @Description: 删除评论
	 * @author xyl
	 * @date 2021-01-22 
	 */
	@Override
	public void delComment(Integer commentId) {
		// TODO Auto-generated method stub
		CommentExample commentExample = new CommentExample();
		commentExample.createCriteria().andCommentIdEqualTo(commentId);
		Comment comment =new Comment();
		comment.setDelState(2);
		commentMapper.updateByExampleSelective(comment, commentExample);
	}

	//我的帖子
	@Override
	public List<Post> myPost(Integer memberId) {
		// TODO Auto-generated method stub
		PostExample postExample = new PostExample();
		postExample.setOrderByClause("create_time desc");
		postExample.createCriteria().andMemberIdEqualTo(memberId).andDelStateEqualTo(1);
		List<Post> postList = postMapper.selectByExample(postExample);
		if (postList.size()>0 && !postList.isEmpty()) {
			//格式化时间戳
			for (Post p:postList) {
				p.setCreateTimeFormat(p.getCreateTime().getTime());
				//查询评论数量
				CommentExample commentExample = new CommentExample();
				commentExample.createCriteria().andPostIdEqualTo(p.getPostId()).andDelStateEqualTo(1);
				p.setCommentNum(commentMapper.countByExample(commentExample));
				MemberExample memberExample = new MemberExample();
				memberExample.createCriteria().andDelStateEqualTo(1).andMemberIdEqualTo(p.getMemberId());
				List<Member> memberList = memberMapper.selectByExample(memberExample);
				Member m = new Member();
				if(memberList.size()>0 && !memberList.isEmpty()) {
					m = memberList.get(0);
					Map<String, Object> map = new HashMap<String, Object>();
					
					TbstaticExample staticExample1 = new TbstaticExample();
					staticExample1.createCriteria().andIdEqualTo(m.getDepartment()).andDelStateEqualTo(1);
					map.put("department", staticMapper.selectByExample(staticExample1).get(0).getDescription());
					
					TbstaticExample staticExample2 = new TbstaticExample();
					staticExample2.createCriteria().andIdEqualTo(m.getMajor()).andDelStateEqualTo(1);
					map.put("majormajor", staticMapper.selectByExample(staticExample2).get(0).getDescription());
					
					TbstaticExample staticExample3 = new TbstaticExample();
					staticExample3.createCriteria().andIdEqualTo(m.getGrades()).andDelStateEqualTo(1);
					map.put("grades", staticMapper.selectByExample(staticExample3).get(0).getDescription());
					m.setMap(map);
				}
				p.setMember(m);
			}
		}
		return postList;
	}

}

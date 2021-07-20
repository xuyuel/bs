package com.sjzc.kt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjzc.kt.entity.Comment;
import com.sjzc.kt.entity.Post;
import com.sjzc.kt.service.CommentService;
import com.sjzc.kt.util.R;

@RestController
@RequestMapping("comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * @Title: addPost
	 * @Description: 新增课下交流问题
	 * @author xyl
	 * @date 2021-01-22
	 */
	@PostMapping("/addPost")
	public R addPost(@RequestBody Post post) {

		commentService.addPost(post);
		return R.ok("添加成功");
	}

	/**
	 * @Title: selectPostList
	 * @Description: 学生移动端查询问题列表
	 * @author xyl
	 * @date 2021-01-24
	 */
	@GetMapping("/selectPostList")
	public R selectPostList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Post> postList = commentService.selectPostList();
		PageInfo<Post> pageInfo = new PageInfo<Post>(postList);
		return R.ok().put("postList", pageInfo);
	}

	/**
	 * @Title: selectPostById
	 * @Description: 查询问题详情
	 * @author xyl
	 * @date 2021-01-24
	 */
	@GetMapping("/selectPostById")
	public R selectPostById(@RequestParam("postId") Integer postId) {
		Post post = commentService.selectPostById(postId);
		return R.ok().put("post", post);
	}

	/**
	 * @Title: addComment
	 * @Description: 添加评论
	 * @author xyl
	 * @date 2021-01-24
	 */
	@PostMapping("/addComment")
	public R addComment(@RequestBody Comment comment) {
		
		commentService.addComment(comment);
		return R.ok("评论成功");
	}

	/**
	 * @Title: selectFirstComment
	 * @Description: 查询一级评论
	 * @author xyl
	 * @date 2021-01-25
	 */
	@GetMapping("/selectComment")
	public R selectFirstComment(@RequestParam("postId") Integer postId, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("pageSize") Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Comment> firstCommentList = commentService.selectComment(postId);
		PageInfo<Comment> pageInfo = new PageInfo<Comment>(firstCommentList);
		return R.ok().put("firstCommentList", firstCommentList);
	}

	/**
	 * @Title: selectAllComment
	 * @Description: 查询一级评论下的评论
	 * @author xyl
	 * @date 2021-01-25
	 */
	/*@GetMapping("/selectAllComment")
	public R selectAllComment(@RequestParam("commentId") Integer commentId) {
		List<Comment> commentList = commentService.selectAllComment(commentId);
		return R.ok().put("commentList", commentList);
	}*/

	/**
	 * @Title: delPost
	 * @Description: 删除问题
	 * @author xyl
	 * @date 2021-01-25
	 */
	@GetMapping("/delPost")
	public R delPost(@RequestParam("postId") Integer postId) {
		commentService.delPost(postId);
		return R.ok("删除成功");
	}

	/**
	 * @Title: delComment
	 * @Description: 删除评论
	 * @author xyl
	 * @date 2021-01-26
	 */
	@GetMapping("/delComment")
	public R delComment(@RequestParam("commentId") Integer commentId) {
		commentService.delComment(commentId);
		return R.ok("删除成功");
	}
	
	//查询我的问题
	@GetMapping("/myPost")
	public R myPost(@RequestParam("memberId") Integer memberId, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("pageSize") Integer pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		List<Post> postList = commentService.myPost(memberId);
		PageInfo<Post> pageInfo = new PageInfo<Post>(postList);
		return R.ok().put("postList", pageInfo);
	}
}

package com.sjzc.kt.entity;

import java.util.Date;

public class Post {
    private Integer postId;

    private Integer courseId;

    private Integer classRelationId;

    private Integer memberId;

    private String postContent;

    private Date createTime;

    private Date modifyTime;

    private Integer delState;
    
    private Member member;
    
    private long createTimeFormat;
    
    private Integer commentNum;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getClassRelationId() {
        return classRelationId;
    }

    public void setClassRelationId(Integer classRelationId) {
        this.classRelationId = classRelationId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent == null ? null : postContent.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getDelState() {
        return delState;
    }

    public void setDelState(Integer delState) {
        this.delState = delState;
    }

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public long getCreateTimeFormat() {
		return createTimeFormat;
	}

	public void setCreateTimeFormat(long createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	
	
}
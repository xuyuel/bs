package com.sjzc.kt.entity;

import java.util.Date;
import java.util.List;

public class Comment {
    private Integer commentId;

    private Integer postId;

    private Integer memberId;

    private Integer parent;

    private String commentContent;

    private Date createTime;

    private Date modifyTime;

    private Integer delState;
    
    /*private String replyMemberName;
    
    private Integer replyMemberId;
    
    private String memberName;*/
    
    private List<Comment> commentList;
    
    private Member member;
    
    private long createTimeFormat;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
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

	/*public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getReplyMemberName() {
		return replyMemberName;
	}

	public void setReplyMemberName(String replyMemberName) {
		this.replyMemberName = replyMemberName;
	}

	public Integer getReplyMemberId() {
		return replyMemberId;
	}

	public void setReplyMemberId(Integer replyMemberId) {
		this.replyMemberId = replyMemberId;
	}*/

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
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
	
}
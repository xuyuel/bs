package com.sjzc.kt.entity;

import java.util.Date;

public class TestChoice {
    private Integer choiceId;

    private Integer questionId;

    private String choiceContent;

    private Integer rightAnswer;

    private Integer choiceSort;

    private Date createTime;

    private Date modifyTime;

    private Integer delState;
    
    //是否被选择 1是 2否
    private Integer selectPoint;

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getChoiceContent() {
        return choiceContent;
    }

    public void setChoiceContent(String choiceContent) {
        this.choiceContent = choiceContent == null ? null : choiceContent.trim();
    }

    public Integer getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Integer rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Integer getChoiceSort() {
        return choiceSort;
    }

    public void setChoiceSort(Integer choiceSort) {
        this.choiceSort = choiceSort;
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

	public Integer getSelectPoint() {
		return selectPoint;
	}

	public void setSelectPoint(Integer selectPoint) {
		this.selectPoint = selectPoint;
	}
    
}
package com.sjzc.kt.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestQuestion {
    private Integer questionId;

    private Integer testId;

    private String questionName;

    private Integer sort;

    private Date createTime;

    private Date modifyTime;

    private Integer delState;
    
    private List<TestChoice> testChoiceList;
    
    private Integer choiceId;
    
    private Map<String, Object> answerMap;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName == null ? null : questionName.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

	public List<TestChoice> getTestChoiceList() {
		return testChoiceList;
	}

	public void setTestChoiceList(List<TestChoice> testChoiceList) {
		this.testChoiceList = testChoiceList;
	}

	public Map<String, Object> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<String, Object> answerMap) {
		this.answerMap = answerMap;
	}

	public Integer getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(Integer choiceId) {
		this.choiceId = choiceId;
	}
	
}
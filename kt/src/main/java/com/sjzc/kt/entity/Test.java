package com.sjzc.kt.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Test {
    private Integer testId;

    private String testName;

    private Integer scheduleId;

    private Integer classRelationId;

    private String description;

    private Integer state;

    private Integer teacherId;

    private String durationTime;

    private Date createTime;

    private Date modifyTime;

    private Integer delState;
    
    private List<TestQuestion> testQuestionList;
    
    private Integer pageSize;
    
    private Integer pageNum;
    
    private long createTimeFormat;
    
    private long answerTimeFormat;
    
    private long releaseTimeFormat;
    
    private Map<String, Object> answerMap;
    
    private Integer memberId;


    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName == null ? null : testName.trim();
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getClassRelationId() {
        return classRelationId;
    }

    public void setClassRelationId(Integer classRelationId) {
        this.classRelationId = classRelationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime == null ? null : durationTime.trim();
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

	public List<TestQuestion> getTestQuestionList() {
		return testQuestionList;
	}

	public void setTestQuestionList(List<TestQuestion> testQuestionList) {
		this.testQuestionList = testQuestionList;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public long getCreateTimeFormat() {
		return createTimeFormat;
	}

	public void setCreateTimeFormat(long createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}

	public Map<String, Object> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<String, Object> answerMap) {
		this.answerMap = answerMap;
	}

	public long getAnswerTimeFormat() {
		return answerTimeFormat;
	}

	public void setAnswerTimeFormat(long answerTimeFormat) {
		this.answerTimeFormat = answerTimeFormat;
	}

	public long getReleaseTimeFormat() {
		return releaseTimeFormat;
	}

	public void setReleaseTimeFormat(long releaseTimeFormat) {
		this.releaseTimeFormat = releaseTimeFormat;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	
}
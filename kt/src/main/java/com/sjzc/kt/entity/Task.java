package com.sjzc.kt.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Task {
    private Integer taskId;

    private String title;

    private String description;

    private Integer scheduleId;

    private Integer classRelationId;

    private Integer state;

    private Integer teacherId;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date modifyTime;

    private Integer delState;
    
    private List<TaskQuestion> taskQuestionsList;
    
    private String taskAnswer;
    
    private Integer pageSize;
    
    private Integer pageNum;
    
    private long startTimeFormat;
    
    private long endTimeFormat;
    
    private long createTimeFormat;
    
    private Integer score;
    
    private Map<String, Object> map;
    
    private Integer memberId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

	public List<TaskQuestion> getTaskQuestionsList() {
		return taskQuestionsList;
	}

	public void setTaskQuestionsList(List<TaskQuestion> taskQuestionsList) {
		this.taskQuestionsList = taskQuestionsList;
	}

	public String getTaskAnswer() {
		return taskAnswer;
	}

	public void setTaskAnswer(String taskAnswer) {
		this.taskAnswer = taskAnswer;
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

	public long getStartTimeFormat() {
		return startTimeFormat;
	}

	public void setStartTimeFormat(long startTimeFormat) {
		this.startTimeFormat = startTimeFormat;
	}

	public long getEndTimeFormat() {
		return endTimeFormat;
	}

	public void setEndTimeFormat(long endTimeFormat) {
		this.endTimeFormat = endTimeFormat;
	}

	public long getCreateTimeFormat() {
		return createTimeFormat;
	}

	public void setCreateTimeFormat(long createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
}
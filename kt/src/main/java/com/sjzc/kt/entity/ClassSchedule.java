package com.sjzc.kt.entity;

import java.util.Date;

public class ClassSchedule {
    private Integer id;

    private Integer courseId;

    private Integer classRelationId;

    private String courseName;

    private String weekth;

    private String schoolWeekth;

    private String day;

    private Integer section;

    private Integer department;

    private Integer major;

    private Integer grades;

    private Integer teacherId;

    private Date createTime;

    private Date modifyTime;

    private Integer delState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getWeekth() {
        return weekth;
    }

    public void setWeekth(String weekth) {
        this.weekth = weekth == null ? null : weekth.trim();
    }

    public String getSchoolWeekth() {
        return schoolWeekth;
    }

    public void setSchoolWeekth(String schoolWeekth) {
        this.schoolWeekth = schoolWeekth == null ? null : schoolWeekth.trim();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getGrades() {
        return grades;
    }

    public void setGrades(Integer grades) {
        this.grades = grades;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
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
}
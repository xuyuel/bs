package com.sjzc.kt.entity;

import java.util.Date;
import java.util.List;

public class Tbstatic {
    private Integer id;

    private Integer type;

    private Integer parent;

    private String description;

    private Integer sort;

    private Date createTime;

    private Date modifyTime;

    private Integer delState;
    
    private List<Tbstatic> tbstaticList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

	public List<Tbstatic> getTbstaticList() {
		return tbstaticList;
	}

	public void setTbstaticList(List<Tbstatic> tbstaticList) {
		this.tbstaticList = tbstaticList;
	}
    
}
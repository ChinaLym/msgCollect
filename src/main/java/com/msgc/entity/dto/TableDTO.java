package com.msgc.entity.dto;

import com.msgc.entity.Table;

import java.util.Date;

public class TableDTO {

	private Integer id;

	private String name;

	private Integer owner;

	private String ownerName;

	private Integer filledNum;

	private Integer maxFillNum;

	private String state;

	private String detail;

	private String secretKey;

	private Boolean visibility;

	private Date createTime;

	private Date updateTime;

	private Date publishTime;

	private Date startTime;

	private Date endTime;

	public TableDTO() {
	}

	public TableDTO(Table table) {
		this.id = table.getId();
		this.name = table.getName();
		this.owner = table.getOwner();
		this.ownerName = table.getName();
		this.filledNum = table.getFilledNum();
		this.maxFillNum = table.getMaxFillNum();
		this.state = table.getState();
		this.detail = table.getDetail();
		this.secretKey = table.getSecretKey();
		this.visibility = table.getVisibility();
		this.createTime = table.getCreateTime();
		this.updateTime = table.getUpdateTime();
		this.publishTime = table.getPublishTime();
		this.startTime = table.getStartTime();
		this.endTime = table.getEndTime();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getFilledNum() {
		return filledNum;
	}

	public void setFilledNum(Integer filledNum) {
		this.filledNum = filledNum;
	}

	public Integer getMaxFillNum() {
		return maxFillNum;
	}

	public void setMaxFillNum(Integer maxFillNum) {
		this.maxFillNum = maxFillNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
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
}
package com.msgc.entity.dto;

import com.msgc.entity.Group;

import java.util.Date;

public class GroupDTO {
	private Integer id;

	private Date createTime;

	private String icon;

	private String intro;

	private int masterId;

	private String name;

	private String masterName;

	private int memberNumber;

	private String notice;

	public GroupDTO() {
	}

	public GroupDTO(Group group) {
		this.id = group.getId();
		this.name = group.getName();
		this.masterId = group.getMasterId();
		this.icon = group.getIcon();
		this.intro = group.getIntro();
		this.memberNumber = group.getMemberNumber();
		this.notice = group.getNotice();
		this.createTime = group.getCreateTime();
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public int getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String iconUri) {
		this.icon = iconUri;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getMasterId() {
		return this.masterId;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotice() {
		return this.notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

}
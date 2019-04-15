package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the tb_group database table.
 * 
 */
@Entity
@Table(name="tb_group")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="icon")
	private String icon;

	private String intro;

	@Column(name="master_id")
	private int masterId;

	private String name;


    @Column(name="member_number")
	private int memberNumber;
    
	private String notice;

	public Group() {
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
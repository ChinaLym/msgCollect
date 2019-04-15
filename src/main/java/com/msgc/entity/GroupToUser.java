package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the tb_group_to_user database table.
 * 
 */
@Entity
@Table(name="tb_group_to_user")
@NamedQuery(name="GroupToUser.findAll", query="SELECT g FROM GroupToUser g")
public class GroupToUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="group_id")
	private Integer groupId;

	@Column(name="group_nick")
	private String groupNick;

	@Column(name="user_id")
	private int userId;

	public GroupToUser() {
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

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupNick() {
		return this.groupNick;
	}

	public void setGroupNick(String groupNick) {
		this.groupNick = groupNick;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
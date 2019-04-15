package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the tb_broadcast_message database table.
 * 
 */
@Entity
@Table(name="tb_broadcast_message")
@NamedQuery(name="BroadcastMessage.findAll", query="SELECT m FROM BroadcastMessage m")
public class BroadcastMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="creater_id")
	private Integer createrId;

	@Column(name="is_compel")
	private Boolean compel;

	@Column(name="is_delete")
	private Boolean delete;

	@Column(name="is_sys")
	private Boolean sys;

	private String title;

	public BroadcastMessage() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreaterId() {
		return this.createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	public Boolean getCompel() {
		return this.compel;
	}

	public void setCompel(Boolean compel) {
		this.compel = compel;
	}

	public Boolean getDelete() {
		return this.delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getSys() {
		return this.sys;
	}

	public void setSys(Boolean sys) {
		this.sys = sys;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
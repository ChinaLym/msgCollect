package com.msgc.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the tb_table database table.
 * 
 */
@Entity
@javax.persistence.Table(name="tb_table")
@NamedQuery(name="Table.findAll", query="SELECT t FROM Table t")
public class Table implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	private Date endTime;

	private String name;

	private Integer owner;

	private Integer maxFillNum;

	private Integer filledNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="publish_time")
	private Date publishTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	private Date startTime;

	private String state;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	private String detail;

	private String secretKey;

	private Boolean visibility;

	public Table() {
	}

	public String getDetail() {
		return detail;
	}


	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOwner() {
		return this.owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getVisibility() {
		return this.visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "Table{" +
				"id=" + id +
				", createTime=" + createTime +
				", endTime=" + endTime +
				", name='" + name + '\'' +
				", owner=" + owner +
				", maxFillNum=" + maxFillNum +
				", filledNum=" + filledNum +
				", publishTime=" + publishTime +
				", startTime=" + startTime +
				", state='" + state + '\'' +
				", updateTime=" + updateTime +
				", detail='" + detail + '\'' +
				", secretKey='" + secretKey + '\'' +
				", visibility=" + visibility +
				'}';
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
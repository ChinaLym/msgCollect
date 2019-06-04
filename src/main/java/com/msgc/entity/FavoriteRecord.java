package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the tb_unfilled_record database table.
 * 
 */
@Entity
@Table(name="tb_favorite_record")
@NamedQuery(name="FavoriteRecord.findAll", query="SELECT u FROM FavoriteRecord u")
public class FavoriteRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="table_id")
	private Integer tableId;

	@Column(name="table_name")
	private String tableName;

	@Column(name="is_filled")
	private Boolean filled;

	@Column(name="is_delete")
	private Boolean delete;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	public FavoriteRecord() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getDelete() {
		return this.delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getTableId() {
		return this.tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getFilled() {
		return filled;
	}

	public void setFilled(Boolean filled) {
		this.filled = filled;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
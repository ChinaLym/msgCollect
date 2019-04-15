package com.msgc.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the tb_table_group_white_list database table.
 * 
 */
@Entity
@Table(name="tb_table_group_white_list")
@NamedQuery(name="TableGroupWhiteList.findAll", query="SELECT t FROM TableGroupWhiteList t")
public class TableGroupWhiteList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	@Column(name="group_id")
	private Integer groupId;

	@Column(name="table_id")
	private Integer tableId;

	public TableGroupWhiteList() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getTableId() {
		return this.tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

}
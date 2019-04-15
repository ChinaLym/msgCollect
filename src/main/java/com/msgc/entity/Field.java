package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the tb_field database table.
 * 
 */
@Entity
@Table(name="tb_field")
@NamedQuery(name="Field.findAll", query="SELECT f FROM Field f")
public class Field implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="is_required")
	private Boolean required;

	@Column(name="max_length")
	private Integer maxLength;

	private String name;

	private String defaultValue;

	private Integer num;

	@Column(name="table_id")
	private Integer tableId;

	private String type;

	private Boolean visibility;

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Field() {
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getTableId() {
		return this.tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getVisibility() {
		return this.visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "Field{" +
				"id=" + id +
				", tableId=" + tableId +
				", name='" + name + '\'' +
				", num=" + num +
				", type=" + type +
				", minLength=" + maxLength +
				", required=" + required +
				", visibility=" + visibility +
				", defaultValue='" + defaultValue + '\'' +
				'}';
	}
}
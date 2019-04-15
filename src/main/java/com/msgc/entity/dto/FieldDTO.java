package com.msgc.entity.dto;

import com.msgc.entity.Field;
import com.msgc.entity.FieldType;

import java.io.Serializable;


public class FieldDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Boolean required;

	private Integer maxLength;

	private String name;

	private String defaultValue;

	private Integer num;

	private Integer tableId;

	private FieldType type;

	private Boolean visibility;

	public FieldDTO() {

	}

	public FieldDTO(Field field, FieldType fieldType) {
		this.required = field.getRequired();
		this.maxLength =field.getMaxLength();
		this.name = field.getName();
		this.defaultValue = field.getDefaultValue();
		this.num = field.getNum();
		this.tableId = field.getTableId();
		this.type = fieldType;
		this.visibility = field.getVisibility();
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}
}
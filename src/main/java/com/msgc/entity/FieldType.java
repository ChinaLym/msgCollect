package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the tb_field_type database table.
 * 
 */
@Entity
@Table(name="tb_field_type")
@NamedQuery(name="FieldType.findAll", query="SELECT f FROM FieldType f")
public class FieldType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String htmlType;

	private String htmlClass;

	private String htmlPattern;

	private String htmlTitle;

	private String note;

	public FieldType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getHtmlClass() {
		return htmlClass;
	}

	public void setHtmlClass(String htmlClass) {
		this.htmlClass = htmlClass;
	}

	public String getHtmlType() {
		return htmlType;
	}

	public void setHtmlType(String htmlType) {
		this.htmlType = htmlType;
	}

	public String getHtmlPattern() {
		return htmlPattern;
	}

	public void setHtmlPattern(String htmlPattern) {
		this.htmlPattern = htmlPattern;
	}

	public String getHtmlTitle() {
		return htmlTitle;
	}

	public void setHtmlTitle(String htmlTitle) {
		this.htmlTitle = htmlTitle;
	}

	@Override
	public String toString() {
		return "FieldType{" +
				"id=" + id +
				", name='" + name + '\'' +
				", htmlType='" + htmlType + '\'' +
				", htmlClass='" + htmlClass + '\'' +
				", htmlPattern='" + htmlPattern + '\'' +
				", htmlTitle='" + htmlTitle + '\'' +
				", note='" + note + '\'' +
				'}';
	}
}
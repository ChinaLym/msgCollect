package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the tb_answer_str database table.
 * 
 */
@Entity
@Table(name="tb_answer")
@NamedQuery(name="Answer.findAll", query="SELECT a FROM Answer a")
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="answer_record_id")
	private Integer answerRecordId;

	@Column(name="field_id")
	private Integer fieldId;

	private String type;

	private String content;

	public Answer() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAnswerRecordId() {
		return answerRecordId;
	}

	public void setAnswerRecordId(Integer answerRecordId) {
		this.answerRecordId = answerRecordId;
	}
}
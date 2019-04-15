package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The persistent class for the tb_message_type database table.
 * 
 */
@Entity
@Table(name="tb_message_type")
@NamedQuery(name="MessageType.findAll", query="SELECT m FROM MessageType m")
public class MessageType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String note;

	public MessageType() {
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

}
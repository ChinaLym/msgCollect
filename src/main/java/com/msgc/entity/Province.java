package com.msgc.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Table;


/**
 * The persistent class for the tb_province database table.
 * 
 */
@Entity
@Table(name="tb_province")
@NamedQuery(name="Province.findAll", query="SELECT p FROM Province p")
public class Province implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer code;

	private String name;

	public Province() {
	}

	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
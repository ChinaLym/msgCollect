package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the tb_city database table.
 * 
 */
@Entity
@Table(name="tb_city")
@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer code;

	private String name;

	@Column(name="province_code")
	private Integer provinceCode;

	public City() {
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

	public Integer getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(Integer provinceCode) {
		this.provinceCode = provinceCode;
	}

}
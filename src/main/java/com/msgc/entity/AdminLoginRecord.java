package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the  tb_admin_login_record database table.
 * 
 */
@Entity
@Table(name="tb_admin_login_record")
@NamedQuery(name="AdminLoginRecord.findAll", query="SELECT a FROM AdminLoginRecord a")
public class AdminLoginRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String account;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	private String ip;

	@Column(name="is_pass")
	private Boolean pass;

	private String position;

	public AdminLoginRecord() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Boolean getPass() {
		return this.pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
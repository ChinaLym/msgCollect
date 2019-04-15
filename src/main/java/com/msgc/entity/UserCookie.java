package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the tb_user_cookie database table.
 *
 */
@Entity
@Table(name="tb_user_cookie")
@NamedQuery(name="UserCookie.findAll", query="SELECT c FROM UserCookie c")
public class UserCookie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private int userId;

	@Column(name="cookie")
	private String cookie;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiration_date")
	private Date expirationDate;

	public UserCookie() {
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserCookie{" +
				"id='" + cookie + '\'' +
				", expirationDate=" + expirationDate +
				", userId=" + userId +
				'}';
	}
}
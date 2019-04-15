package com.msgc.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the tb_message_user database table.
 * 
 */
@Entity
@Table(name="tb_message_user")
@NamedQuery(name="MessageUser.findAll", query="SELECT u FROM MessageUser u")
public class MessageUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="message_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer messageId;

	@Column(name="to_user_id")
	private Integer toUserId;

	@Column(name="is_delete")
	private Boolean delete;

	@Column(name="is_read")
	private Boolean read;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="read_time")
	private Date readTime;

	public MessageUser() {
	}

	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getToUserId() {
		return this.toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public Boolean getDelete() {
		return this.delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getIread() {
		return this.read;
	}

	public void setIread(Boolean iread) {
		this.read = read;
	}

	public Date getReadTime() {
		return this.readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

}
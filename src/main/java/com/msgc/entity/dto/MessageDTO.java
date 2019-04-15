package com.msgc.entity.dto;

import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.entity.Message;

import java.util.Date;

public class MessageDTO{

	private String id;

	private Integer receiver;

	private String title;

	private String content;

	private String href;

	private String type;

	private Boolean read;

	private Date createTime;

	private Boolean delete;

	public MessageDTO() {
	}

	public MessageDTO(Message message) {
		this.id = message.getId();
		this.receiver = message.getReceiver();
		this.title = message.getTitle();
		this.content = message.getContent();
		this.href = message.getHref();
		this.type = MessageTypeEnum.getNameBy(message.getType());
		this.read = message.getRead();
		this.createTime = message.getCreateTime();
		this.delete = message.getDelete();
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getReceiver() {
		return receiver;
	}

	public void setReceiver(Integer receiver) {
		this.receiver = receiver;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}
}
package com.msgc.entity.dto;

import com.msgc.entity.Comment;

import java.util.Date;
import java.util.List;

public class CommentDTO {

	private Integer id;

	private String content;

	private Date createTime;

	private Integer userId;

	private String userName;

	private String userHeadImage;

	private List<CommentDTO> replyList;

	public CommentDTO() {
	}

	public CommentDTO(Comment comment) {
		this.id = comment.getId();
		this.content = comment.getContent();
		this.createTime = comment.getCreateTime();
		this.userId = comment.getUserId();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHeadImage() {
		return userHeadImage;
	}

	public void setUserHeadImage(String userHeadImage) {
		this.userHeadImage = userHeadImage;
	}

	public List<CommentDTO> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<CommentDTO> replyList) {
		this.replyList = replyList;
	}
}
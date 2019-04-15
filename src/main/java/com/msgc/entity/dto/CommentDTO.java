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

	//如果本条是回复，则该字段表示目标评论发表人的昵称，这里限制不能回复他人的回复，不使用该字段
	//private String aimName;

	//回复这条评论的评论，这里使用Comment来限制不能回复他人的回复
	private List<Comment> replyList;

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

	public List<Comment> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Comment> replyList) {
		this.replyList = replyList;
	}
}
package com.msgc.entity.bo;

import com.msgc.entity.Answer;
import com.msgc.entity.AnswerRecord;
import com.msgc.utils.IPUtil;

import java.util.Date;
import java.util.List;

public class AnswerRecordBO {

	private Integer id;

	private Integer tableId;

	private String ip;

	private String deviceSystem;

	private String browser;

	private String userRealName;

	private Date update_time;

	private transient List<Answer> answerList;

	public AnswerRecordBO() {
	}

	/**
	 * 已经将 IP 转化为 IPv4地址 字符串
	 * @param answerRecord  根据该参数初始化
	 * @param answerList	合并该 answer 列表
	 */
	public AnswerRecordBO(AnswerRecord answerRecord, List<Answer> answerList) {
		id = answerRecord.getId();
		tableId = answerRecord.getTableId();
		ip = IPUtil.intToIPv4Str(answerRecord.getIp());
		userRealName = answerRecord.getUserRealName();
		deviceSystem = answerRecord.getDeviceSystem();
		browser = answerRecord.getBrowser();
		update_time = answerRecord.getUpdate_time();
		this.answerList = answerList;
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDeviceSystem() {
		return deviceSystem;
	}

	public void setDeviceSystem(String deviceSystem) {
		this.deviceSystem = deviceSystem;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
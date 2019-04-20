package com.msgc.constant.enums;

/**
 * @ClassName:  MessageTypeEnum
 * @Description:
 *      消息类型（生命周期）
 * @Author:         LYM
 * @CreateDate:     2019年4月15日
*/
public enum MessageTypeEnum {
	SYSTEM("系统消息", 1),				// 系统发送，比如修改密码提醒，异地登录，xxx未通过审核等..
	COMMENT("评论提醒", 2),				// 他人评论自己的收集表
	REPLY("回复提醒", 3),				// 他人回复了自己的评论
	TABLE_FILLED("填写提醒", 4),			// 他人填写了自己的收集表
	TABLE_END("表截止提醒", 5),			// 我发布的收集表已经截止
	ALL("所有消息", 0);					// 所有消息

	private String name;
	private Integer code;

	MessageTypeEnum(String name, Integer code){
		this.code = code;
		this.name = name;
	}

	public static String getNameBy(Integer code){
		switch (code){
			case 1:
				return "系统消息";
			case 2:
				return "评论提醒";
			case 3:
				return "回复提醒";
			case 4:
				return "填写提醒";
			case 5:
				return "表截止提醒";
			default:
				return "消息";
		}
	}

	public boolean validate(Integer typeCode){
		return typeCode > 0 && typeCode < 4;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}
}

package com.msgc.constant;
/**
* Type: SessionKey
* Description: 规范约束：Session.setAttribute(key, value)和session.getAttribute(key)中的key
* 	必须在该类中列出并注释，并使用该类操作，以解耦与明晰
* @author LYM
* @date Dec 20, 2018
 */
public class SessionKey {

	//登录的用户 User
	public static final String USER = "user";

	//cookie 中的自动登录凭证 String
	public static final String AUTO_LOGIN = "auto-login";

	//未读的消息List	   List<Message>
	public static final String UNREAD_MESSAGE = "unreadMessage";

	//token字段	String
	public static final String TOKEN = "token";

	//近期收集List	List<Table>
    public static final String RECENT_COLLECT = "recentCollect";

    //包含重定向地址	String
    public static final String REDIRECT_URL = "redirectUrl";

    //是否有新消息 标志  Boolean
    public static final String NEW_MESSAGES_FLAG = "newMessagesFlag";

    private SessionKey() {}
}

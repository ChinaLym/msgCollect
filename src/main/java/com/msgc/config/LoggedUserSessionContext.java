package com.msgc.config;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggedUserSessionContext{
	//默认容量为 16 ，可以根据自己网站的访问量设置一个合理的初始值，避免刚开始时候的频繁扩容。
    private static volatile Map<Integer, HttpSession> sessionMap  = new ConcurrentHashMap<>(128);

    public static HttpSession putIfAbsent(Integer userId, HttpSession session) {
    	//先前已经在某个客户端登录了，则返回那个 session，以实现多端登录session共享
	    return sessionMap.putIfAbsent(userId, session);        
    }

    public static void remove(Integer userId) {
        sessionMap.remove(userId);
    }
 
    public static HttpSession getSession(Integer userId) {
        return sessionMap.get(userId);
    }

    public static int size(){
        return sessionMap.size();
    }
}

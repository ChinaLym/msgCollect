package com.msgc.config;

import com.msgc.constant.SessionKey;
import com.msgc.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

    //注意：HttpServletRequest的getSession()方法，如果当前请求没有对应的session会自动创建session。
    //使用getSession(false)就不会创建session，如果没有当前请求对应的session就返回null.

   
    //新 session 创建 如未知用户浏览
    @Override
    public void sessionCreated(HttpSessionEvent event) {

    }

    //session 销毁 用户下线，退出登录
    @Override
    public void sessionDestroyed(HttpSessionEvent event) throws ClassCastException {
        HttpSession session = event.getSession();
        Object userObj = session.getAttribute(SessionKey.USER);
        if(userObj != null){
        	User user = (User)userObj;
        	LoggedUserSessionContext.remove(user.getId());
        }
        
    }

}

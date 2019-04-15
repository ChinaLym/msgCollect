package com.msgc.aop.interceptor;

import com.msgc.constant.SessionKey;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionKey.USER) != null){
            return true;
        }
        response.sendRedirect("/login");
        session.setAttribute(SessionKey.REDIRECT_URL, request.getRequestURI());
        return false;
    }

}

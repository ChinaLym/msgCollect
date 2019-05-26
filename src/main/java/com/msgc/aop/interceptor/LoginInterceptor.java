package com.msgc.aop.interceptor;

import com.msgc.constant.SessionKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Component
public class LoginInterceptor implements HandlerInterceptor {
 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionKey.USER) != null){
            return true;
        }
        response.sendRedirect("/login");
        if("GET".equalsIgnoreCase(request.getMethod())){
            StringBuilder realRequestURL = new StringBuilder(request.getRequestURI());
            Enumeration<String> parameterNames = request.getParameterNames();
            if(parameterNames.hasMoreElements())
                realRequestURL.append("?");
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = request.getParameter(paramName);
                if(StringUtils.isEmpty(paramName)){
                    continue;
                }
                realRequestURL.append(paramName);
                realRequestURL.append("=");
                realRequestURL.append(paramValue);
                realRequestURL.append("&");
            }
            session.setAttribute(SessionKey.REDIRECT_URL, realRequestURL.toString());
        }
        return false;
    }

}

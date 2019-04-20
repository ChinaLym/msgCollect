package com.msgc.utils;

import com.msgc.config.LoggedUserSessionContext;
import com.msgc.constant.SessionKey;
import com.msgc.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
* Type: WebUtil
* Description: 获取spring中的bean，方便调试和检测
* @author LYM
* @date Dec 16, 2018
 */
@Component
public class WebUtil implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(WebUtil.class);
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(WebUtil.applicationContext == null) {
			WebUtil.applicationContext = applicationContext;
		}
		logger.info("ApplicationContext配置成功,applicationContext对象："+ WebUtil.applicationContext);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static String getCookie(String cookieName){
		HttpServletRequest request = WebUtil.getRequest();
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals(cookieName)){
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static void setCookie(String cookieName, String cookieValue, int maxSecond){
		HttpServletResponse response = WebUtil.getResponse();
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxSecond);//有效期单位为秒
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void expireCookie(String cookieName){
		HttpServletResponse response = WebUtil.getResponse();
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(0);//有效期单位为秒
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static HttpServletResponse getResponse(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	public static HttpSession getSession(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	public static HttpSession setSessionUser(User user){
		HttpSession session = LoggedUserSessionContext.getSession(user.getId());
		if(session != null){
			// session 有效时间 30 分钟
			int maxSessionEffectiveSecond = 60 * 30;
			//使用之前登陆过的 id
			WebUtil.setCookie("JSESSIONID", session.getId(), maxSessionEffectiveSecond);
		}else{
			session = WebUtil.getSession();
			session.setAttribute(SessionKey.USER, user);
			LoggedUserSessionContext.putIfAbsent(user.getId(), session);
		}
		return session;
	}

	public static Object getSessionKey(String sessionKeyName){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest().getSession().getAttribute(sessionKeyName);
	}





	/**
	 * 返回访问服务器的路径如：
	 * @return	http://xxx.com:8080 拼接路径时最后需要添加 '/'
	 */
	public static String getServerURL(){
		HttpServletRequest request = getRequest();
		String requestURL = request.getRequestURL().toString();
		StringBuilder serverURL = new StringBuilder("http");
		if(requestURL.indexOf("https") > 0){
			serverURL.append("s://");
		}else{
			serverURL.append("://");
		}
		serverURL.append(request.getServerName());
		serverURL.append(':');
		serverURL.append(request.getServerPort());
		return serverURL.toString();
	}


	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	public static <T> T getBean(String name,Class<T> clazz) {
		return getApplicationContext().getBean(name,clazz);
	}
}
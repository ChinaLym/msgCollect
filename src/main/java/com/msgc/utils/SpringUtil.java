package com.msgc.utils;

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

/**
* Type: SpringUtil
* Description: 获取spring中的bean，方便调试和检测
* @author LYM
* @date Dec 16, 2018
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(SpringUtil.class);
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
		logger.info("ApplicationContext配置成功,applicationContext对象："+SpringUtil.applicationContext);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static String getCookie(String cookieName){
		HttpServletRequest request = SpringUtil.getRequest();
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
		HttpServletResponse response = SpringUtil.getResponse();
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxSecond);//有效期单位为秒
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void expireCookie(String cookieName){
		HttpServletResponse response = SpringUtil.getResponse();
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
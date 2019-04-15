package com.msgc.utils;

import javax.servlet.http.HttpServletRequest;

/**
* Type: IPUtil
* Description: 工具类，主要为了生成和检验验证码
* @author LYM
* @date Dec 18, 2018
 */
public class IPUtil {
	
	/**
	 * Title: getIpAddr
	 * Description: 获取IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	}

	/**
	 * (ipStr转Integer)
	 * 方法名：ipStrToInt
	 * 创建人：LYM
	 */
	public static int ipv4StrToInt(String ip){
		String[] ips = ip.split("\\.");
		int ipFour = 0;
		//因为每个位置最大255，刚好在2进制里表示8位
		for(String ip4: ips){
			Integer ip4a = Integer.parseInt(ip4);
			//这里应该用+也可以,但是位运算更快
			ipFour = (ipFour << 8) | ip4a;
		}
		return ipFour;
	}

	/**
	 *
	 * (Integer转IP)
	 * 方法名：intToIPStr
	 * 创建人：LYM
	 */
	public static String intToIPv4Str(Integer ip){
		//思路很简单，每8位拿一次，就是对应位的IP
		StringBuilder sb = new StringBuilder();
		int segment;
		for(int i = 3; i >= 0; i--){
			segment = (ip >> (8 * i)) & (0xff);
			sb.append(segment);
			if(i != 0) {
				sb.append('.');
			}
		}
		return sb.toString();
	}

}

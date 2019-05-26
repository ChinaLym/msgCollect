package com.msgc.annotation;

import com.msgc.aop.interceptor.LoginRequiredInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
* Type: LoginRequired
* Description: 该注解所在的方法需要已经登录才能访问
* 实现类为 {@link LoginRequiredInterceptor}
* @author LYM
* @date Dec 16, 2018
 * @deprecated 本系统中使用AOP拦截所有未登录请求，登录、注册除外
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}

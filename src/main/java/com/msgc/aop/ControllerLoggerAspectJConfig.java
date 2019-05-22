package com.msgc.aop;

import com.msgc.utils.IPUtil;
import com.msgc.utils.WebUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
* Type: ControllerLoggerAspectJConfig
* Description: 
* com.msgc.controller中所有以Controller结尾的类中的所有public方法
* 或者添加@ControllerLogger注解的方法，都会前后记录日志及异常
* @author LYM
* @date Dec 16, 2018
 */
@Aspect
@Component("ControllerLoggerAspectJConfig")
public class ControllerLoggerAspectJConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLoggerAspectJConfig.class);

    //定义一个切点,代表service下所有public方法且有xxxMapping注解或者添加@ControllerLogger注解的方法，都会前后记录日志及异常
    @Pointcut("execution(public * com.msgc.controller.*Controller.*(..)) " +
            "&& @annotation(com.msgc.annotation.ControllerLogger)" + 
    		"|| @annotation(org.springframework.web.bind.annotation.GetMapping)" + 
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" + 
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void log() {}

    /**
     * Title: beforeControllerLogger
     * Description: 进入方法前要做的事情：记录方法名和入参
     * @param joinPoint 日志记录切点
     */
    @Before("log()")
    public void beforeControllerLogger(JoinPoint joinPoint) {
		HttpServletRequest request = WebUtil.getRequest();
		// 记录请求内容
		StringBuilder requestContent = new StringBuilder("( ");
		requestContent.append(request.getMethod()).append(" ) URL : ");
		requestContent.append(request.getRequestURL().toString());
		requestContent.append(" From -IP : ").append(IPUtil.getRequestIpAddress(request)).append(" —— aim: ");
		// 包+类名
		requestContent.append(joinPoint.getSignature().getDeclaringTypeName()).append('.');
		// 方法名
		requestContent.append(joinPoint.getSignature().getName());

    	Object[] args = joinPoint.getArgs();    	
    	System.out.println(joinPoint.getSignature().toShortString());
	    requestContent.append("--entry args : ");
	    requestContent.append(Arrays.toString(args));
	    LOGGER.info(requestContent.toString());
    }

    /**
     * Title: afterControllerLogger
     * Description:  切点所在函数正常返回之后要做的事情：记录方法名和返回值
     * @param joinPoint 切入点
     * @param returnObject	返回值（返回的数据）
     */
    @AfterReturning(value = "log()", returning = "returnObject")
    public void afterControllerLogger(JoinPoint joinPoint, Object returnObject) {
    	StringBuilder sb = new StringBuilder(joinPoint.getSignature().toShortString());
	    sb.append("--return object : ");
	    if (returnObject == null) {
	    	sb.append("null.");
		}else {
			sb.append(returnObject.toString());
		}
	    LOGGER.info(sb.toString());
    }
    
    /**
     * Title: handlerExcetionLog
     * Description: 出现异常要做的事情：记录方法名，异常类型和描述
     * @param joinPoint	切点
     * @param exception	异常
     */
    @AfterThrowing(value = "log()", throwing = "exception")
	public void handlerExceptionLog(JoinPoint joinPoint, Exception exception) {
		// 获得类名和方法名称
	    StringBuilder sb = new StringBuilder(joinPoint.getSignature().toShortString());
	    sb.append("--exception! : ");
	    sb.append(exception.getClass());
	    sb.append(" : ");
	    sb.append(exception.getMessage());
	    LOGGER.info(sb.toString());
    }
}
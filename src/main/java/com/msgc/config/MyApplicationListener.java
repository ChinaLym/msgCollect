package com.msgc.config;

import com.msgc.notify.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<ContextStoppedEvent> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        MessageSender.getInstance().stop();
        logger.info("send order to messageSender stop!");
    }
        //spring boot 生命周期事件

        // 初始化环境变量
        /*if (event instanceof ApplicationEnvironmentPreparedEvent){  }
        // 初始化完成
        else if (event instanceof ApplicationPreparedEvent){  }
        // 应用刷新
        else if (event instanceof ContextRefreshedEvent) { }
        // 应用已启动完成
        //else if (event instanceof ApplicationReadyEvent) { }
        //应用启动，需要在代码动态添加监听器才可捕获
        //else if (event instanceof ContextStartedEvent) {  }
        // 应用停止
        else if (event instanceof ContextStoppedEvent) { }
        // 应用关闭
        else if(event instanceof ContextClosedEvent) {  }
        else{}*/


}
package com.msgc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class MCSApplicationListener implements ApplicationListener<ContextClosedEvent> {
    private Logger logger = LoggerFactory.getLogger(getClass());

/*    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("程序启动");
    }*/
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("程序停止");
    }

}
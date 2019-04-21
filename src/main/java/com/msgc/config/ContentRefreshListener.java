package com.msgc.config;

import com.msgc.notify.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContentRefreshListener implements ApplicationListener<ContextRefreshedEvent>{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(MessageSender.getInstance()).start();
        logger.info("send order to messageSender start!");
    }
}
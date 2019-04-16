package com.msgc.notify;

import com.msgc.config.LoggedUserSessionContext;
import com.msgc.constant.SessionKey;
import com.msgc.entity.Message;
import com.msgc.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * MessageSender 高耦合的类，业务增加时需要解耦
 */
@Component
public class MessageSender implements Runnable {

    private volatile boolean isRuning = true;

    @Autowired
    IMessageService messageService;

    public void stop(){
        isRuning = false;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start success.");
        while (isRuning){
            try{
                Message message = MessageQueue.take();
                //消费 Message
                Integer userId = message.getReceiver();
                HttpSession session = LoggedUserSessionContext.getSession(userId);
                if(session != null){
                    session.setAttribute(SessionKey.NEW_MESSAGES_FLAG,true);
                }
                //不管用户在不在线，存数据库
                messageService.save(message);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " stop success.");
    }
}

package com.msgc.notify;

import com.msgc.config.LoggedUserSessionContext;
import com.msgc.constant.SessionKey;
import com.msgc.entity.Message;
import com.msgc.service.IMessageService;
import com.msgc.utils.WebUtil;

import javax.servlet.http.HttpSession;

public class MessageSender implements Runnable {

    private volatile boolean isRunning = true;

    private final IMessageService messageService;

    private MessageSender() {
        messageService = WebUtil.getBean(IMessageService.class);
    }
    private static final MessageSender instance = new MessageSender();

    public static MessageSender getInstance(){
        return instance;
    }

    public void stop(){
        isRunning = false;
    }

    @Override
    public void run() {
        if(messageService == null){
            System.err.println("MessageSender start fail !!");
            return;
        }
        System.out.println("MessageSender start success.");
        while (isRunning){
            try{
                Message message = MessageQueue.take();
                //消费 Message
                Integer userId = message.getReceiver();
                HttpSession session = LoggedUserSessionContext.getSession(userId);
                if(session != null){
                    session.setAttribute(SessionKey.NEW_MESSAGES_FLAG,true);
                }
                messageService.save(message);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("MessageSender stop success.");
    }
}

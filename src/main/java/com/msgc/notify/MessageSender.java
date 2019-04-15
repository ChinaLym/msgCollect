package com.msgc.notify;

import com.msgc.config.LoggedUserSessionContext;
import com.msgc.constant.SessionKey;
import com.msgc.entity.Message;

import javax.servlet.http.HttpSession;

/**
 * MessageSender 高耦合的类，业务增加时需要解耦
 */
public class MessageSender implements Runnable {

    private volatile boolean isRuning = true;

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
                if(session == null){
                    //用户不在线，什么也不做
                    continue;
                }else{
                    session.setAttribute(SessionKey.NEW_MESSAGES_FLAG,true);
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " stop success.");
    }
}

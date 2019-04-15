package com.msgc.notify;

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
                String message = MessageQueue.take();
                // TODO 消费 Message

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " stop success.");
    }
}

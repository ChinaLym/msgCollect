package com.msgc.notify;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    // 设置队列大小为 1000 个
    private static final int MAX_QUEUE_SIZE = 1000;

    public static volatile BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>(MAX_QUEUE_SIZE);

    //放不进去拉倒
    public void offer(String message){
        messageQueue.offer(message);
    }

    public static String take() throws InterruptedException {
        return messageQueue.take();
    }
}
package com.msgc.notify;

import com.msgc.entity.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    // 设置队列大小为 1000 个
    private static final int MAX_QUEUE_SIZE = 1000;

    private static volatile BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);

    //放不进去拉倒
    public static void offer(Message message){
        messageQueue.offer(message);
    }

    public static Message take() throws InterruptedException {
        return messageQueue.take();
    }
}
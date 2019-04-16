package com.msgc.service;

import com.msgc.entity.Message;

import java.util.List;

/**
 * Message业务逻辑接口
 */
public interface IMessageService {

    Message save(Message message);

    Message findById(String id);
    
    List<Message> findAllById(List<String> idList);
    
    List<Message> findAll(Message messageExample);
    
    List<Message> findAll();
    
    void deleteById(String id);
    
    List<Message> save(List<Message> messageList);

    Message createMessage(Message message);
}

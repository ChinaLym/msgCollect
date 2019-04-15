package com.msgc.service;

import com.msgc.entity.MessageType;

import java.util.List;

/**
 * MessageType业务逻辑接口
 */
public interface IMessageTypeService {

    MessageType save(MessageType messageType);

    MessageType findById(Integer id);
    
    List<MessageType> findAllById(List<Integer> idList);
    
    List<MessageType> findAll(MessageType messageTypeExample);
    
    List<MessageType> findAll();
    
    void deleteById(Integer id);
    
    List<MessageType> save(List<MessageType> messageTypeList);
}

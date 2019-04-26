package com.msgc.service;

import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.entity.Message;
import com.msgc.entity.Table;

import java.util.List;

/**
 * Message业务逻辑接口
 */
public interface IMessageService {

    Message save(Message message);

    Message findById(String id);
    
    List<Message> findAll(Message messageExample);
    
    void deleteById(String id);
    
    void read(String messageId);

    /**
     * 根据消息类型，收集表信息，自动发送异步消息
     * @param messageType 消息类型
     * @param table         收集表信息
     */
    void sendMessage(MessageTypeEnum messageType, Table table);

    void readAll(Integer typeCode);

    void deleteAllByType(Integer typeCode);

    List<Message> findUnreadByReceiverAndLimit(Integer receiver, int limitNum);
}

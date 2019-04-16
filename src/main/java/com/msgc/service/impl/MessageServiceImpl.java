package com.msgc.service.impl;

import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.entity.Message;
import com.msgc.notify.MessageQueue;
import com.msgc.repository.IMessageRepository;
import com.msgc.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
* Type: MessageServiceImpl
* Description: 业务逻辑实现类
* @author LYM
 */
@Service
public class MessageServiceImpl implements IMessageService{

    private final IMessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }
    
    @Override
    public Message findById(String id) {
        return messageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Message> findAllById(List<String> idList) {
        return messageRepository.findAllById(idList);
    }
    
    @Override
    public List<Message> findAll(Message messageExample) {
        return messageRepository.findAll(Example.of(messageExample));
    }
    
    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
	public void deleteById(String id) {
		messageRepository.deleteById(id);
	}
    
    @Override
    public List<Message> save(List<Message> messageList) {
        return messageRepository.saveAll(messageList);
    }

    @Override
    public Message createMessage(Message message) {
        String title;
        if(MessageTypeEnum.SYSTEM.getCode().equals(message.getType())){
            title = MessageTypeEnum.SYSTEM.getName();
        }else if(MessageTypeEnum.COMMENT.getCode().equals(message.getType())){
            title = MessageTypeEnum.COMMENT.getName();
        }else if(MessageTypeEnum.REPLY.getCode().equals(message.getType())) {
            title = MessageTypeEnum.REPLY.getName();
        }else title = "无标题";
        message.setTitle(title);
        message.setId(UUID.randomUUID().toString());
        message.setRead(false);
        message.setDelete(false);
        message.setCreateTime(new Date());
        MessageQueue.offer(message);
        return message;
    }
}

package com.msgc.service.impl;

import com.msgc.entity.Message;
import com.msgc.repository.IMessageRepository;
import com.msgc.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Message findById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<Message> findAllById(List<Integer> idList) {
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
	public void deleteById(Integer id) {
		messageRepository.deleteById(id);
	}
    
    @Override
    public List<Message> save(List<Message> messageList) {
        return messageRepository.saveAll(messageList);
    }
}

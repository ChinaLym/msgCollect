package com.msgc.service.impl;

import com.msgc.entity.MessageType;
import com.msgc.repository.IMessageTypeRepository;
import com.msgc.service.IMessageTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
/**
* Type: MessageTypeServiceImpl
* Description: 业务逻辑实现类
* @author LYM
 */
@Service
public class MessageTypeServiceImpl implements IMessageTypeService{

    private final IMessageTypeRepository messageTypeRepository;

    @Autowired
    public MessageTypeServiceImpl(IMessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    @Override
    public MessageType save(MessageType messageType) {
        return messageTypeRepository.save(messageType);
    }
    
    @Override
    public MessageType findById(Integer id) {
        Optional<MessageType> messageType = messageTypeRepository.findById(id);
        return messageType.isPresent() ? messageType.get() : null;
    }
    
    @Override
    public List<MessageType> findAllById(List<Integer> idList) {
        return messageTypeRepository.findAllById(idList);
    }
    
    @Override
    public List<MessageType> findAll(MessageType messageTypeExample) {
        return messageTypeRepository.findAll(Example.of(messageTypeExample));
    }
    
    @Override
    public List<MessageType> findAll() {
        return messageTypeRepository.findAll();
    }

    @Override
	public void deleteById(Integer id) {
		messageTypeRepository.deleteById(id);
	}
    
    @Override
    public List<MessageType> save(List<MessageType> messageTypeList) {
        return messageTypeRepository.saveAll(messageTypeList);
    }
}

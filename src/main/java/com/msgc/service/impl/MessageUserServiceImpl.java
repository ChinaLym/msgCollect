package com.msgc.service.impl;

import com.msgc.entity.MessageUser;
import com.msgc.repository.IMessageUserRepository;
import com.msgc.service.IMessageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
/**
* Type: MessageUserServiceImpl
* Description: 业务逻辑实现类
* @author LYM
 */
@Service
public class MessageUserServiceImpl implements IMessageUserService{

    private final IMessageUserRepository messageUserRepository;

    @Autowired
    public MessageUserServiceImpl(IMessageUserRepository messageUserRepository) {
        this.messageUserRepository = messageUserRepository;
    }

    @Override
    public MessageUser save(MessageUser messageUser) {
        return messageUserRepository.save(messageUser);
    }
    
    @Override
    public MessageUser findById(Integer id) {
        Optional<MessageUser> messageUser = messageUserRepository.findById(id);
        return messageUser.orElse(null);
    }
    
    @Override
    public List<MessageUser> findAllById(List<Integer> idList) {
        return messageUserRepository.findAllById(idList);
    }
    
    @Override
    public List<MessageUser> findAll(MessageUser messageUserExample) {
        return messageUserRepository.findAll(Example.of(messageUserExample));
    }
    
    @Override
    public List<MessageUser> findAll() {
        return messageUserRepository.findAll();
    }

    @Override
	public void deleteById(Integer id) {
		messageUserRepository.deleteById(id);
	}
    
    @Override
    public List<MessageUser> save(List<MessageUser> messageUserList) {
        return messageUserRepository.saveAll(messageUserList);
    }
}

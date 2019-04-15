package com.msgc.service;

import com.msgc.entity.MessageUser;

import java.util.List;

/**
 * MessageUser业务逻辑接口
 */
public interface IMessageUserService {

    MessageUser save(MessageUser messageUser);

    MessageUser findById(Integer id);
    
    List<MessageUser> findAllById(List<Integer> idList);
    
    List<MessageUser> findAll(MessageUser messageUserExample);
    
    List<MessageUser> findAll();
    
    void deleteById(Integer id);
    
    List<MessageUser> save(List<MessageUser> messageUserList);
}

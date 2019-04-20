package com.msgc.service.impl;

import com.msgc.constant.SessionKey;
import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.entity.Message;
import com.msgc.entity.Table;
import com.msgc.entity.User;
import com.msgc.notify.MessageQueue;
import com.msgc.repository.IMessageRepository;
import com.msgc.service.IMessageService;
import com.msgc.utils.WebUtil;
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

    //假删除，需要用户 id == receiver
    @Override
	public void deleteById(String id) {
        Integer userId = ((User)WebUtil.getSessionKey(SessionKey.USER)).getId();
		messageRepository.setDeleteStateById(id, userId);
	}

    @Override
    public void deleteAllByType(Integer typeCode) {
        Integer userId = ((User)WebUtil.getSessionKey(SessionKey.USER)).getId();
        messageRepository.setDeleteStateByType(typeCode, userId);
    }

    @Override
    public List<Message> findUnreadByReceiverAndLimit(Integer receiver, int limitNum) {
        return messageRepository.findUnreadByReceiverAndLimit(receiver, limitNum);
    }

    @Override
    public List<Message> save(List<Message> messageList) {
        return messageRepository.saveAll(messageList);
    }

    @Override
    public void read(String messageId) {
        Integer userId = ((User)WebUtil.getSessionKey(SessionKey.USER)).getId();
        messageRepository.setReadStateById(messageId, userId);
    }

    @Override
    public void readAll(Integer messageType) {
        Integer userId = ((User)WebUtil.getSessionKey(SessionKey.USER)).getId();
        messageRepository.setAllReadStateByType(messageType, userId);
    }

    /**
     * 根据消息类型，收集表信息，自动发送消息
     * @param messageType 消息类型
     * @param table         收集表信息
     */
    public void sendMessage(MessageTypeEnum messageType, Table table){
        User user = (User)WebUtil.getSessionKey(SessionKey.USER);
        Message message = new Message();
        if(messageType == MessageTypeEnum.COMMENT){
            // 自己评论自己的表则不发消息
            if(!table.getOwner().equals(user.getId())){
                message.setType(messageType.getCode());
                message.setContent(user.getNickname() + " 填写了您的收集表 " + table.getName());
            }
        }else if(messageType == MessageTypeEnum.REPLY){
            message.setType(messageType.getCode());
            message.setContent(user.getNickname() + " 回复了您  " + table.getName());
        }else if(messageType == MessageTypeEnum.SYSTEM){
            message.setType(MessageTypeEnum.SYSTEM.getCode());
            message.setContent(user.getNickname() + " 参与了您的收集表  " + table.getName());
        }
        message.setReceiver(table.getOwner());
        message.setHref("/collect/data/" + table.getId());
        this.createMessageAndOfferToMQ(message);
    }

    /**
     * 完善消息，并将该条消息防于 MQ
     * @param message 需要发送的消息
     */
    private void createMessageAndOfferToMQ(Message message) {
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
    }
}

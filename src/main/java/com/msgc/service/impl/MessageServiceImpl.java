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
import org.springframework.stereotype.Service;

import java.util.Date;
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

    /**
     * 查询接收者id为 receiverId ，消息类型为 type 且未被删除的消息
     * @param receiverId 接收者 id
     * @param type  消息类型
     * @return  消息列表
     */
    @Override
    public List<Message> findAllByReceiverAndType(Integer receiverId, Integer type) {
        return messageRepository.findAllByReceiverAndTypeAndDelete(receiverId, type, false);
    }
    
    //通过消息Id，假删除，需要用户 id == receiver
    @Override
	public void deleteById(Integer id) {
        Integer userId = ((User)WebUtil.getSessionKey(SessionKey.USER)).getId();
		messageRepository.setDeleteStateById(id, userId);
	}

    /**
     * 删除用户所有某个类型的消息
     * @param typeCode 消息类型
     */
    @Override
    public void deleteAllByType(Integer typeCode) {
        Integer userId = ((User)WebUtil.getSessionKey(SessionKey.USER)).getId();
        messageRepository.setDeleteStateByType(typeCode, userId);
    }

    /**
     * 查询 limitNum 条当前用户未读的消息
     * @param receiver 某个用户
     * @param limitNum 查询条数
     * @return 消息列表
     */
    @Override
    public List<Message> findUnreadByReceiverAndLimit(Integer receiver, int limitNum) {
        return messageRepository.findUnreadByReceiverAndLimit(receiver, limitNum);
    }

    /**
     * 将某条消息标记为已读，未来该操作可以改为通过消息队列异步操作
     * @param messageId 消息 Id
     */
    @Override
    public void read(Integer messageId) {
        Integer userId = ((User)WebUtil.getSessionKey(SessionKey.USER)).getId();
        messageRepository.setReadStateById(messageId, userId);
    }

    /**
     * 将特定类型的消息全部置为已读
     * @param messageType 消息类型
     */
    @Override
    public void readAll(Integer messageType) {
        Integer userId = ((User)WebUtil.getSessionKey(SessionKey.USER)).getId();
        messageRepository.setAllReadStateByType(messageType, userId);
    }

    // 默认向表主人发送
    @Override
    public void sendMessage(MessageTypeEnum messageType, Table table){
        this.sendMessage(messageType, table, table.getOwner());
    }
    /**
     * 根据消息类型，收集表信息，自动发送消息
     * @param messageType 消息类型
     * @param table         收集表信息
     * @param receiver         消息接收者
     */
    @Override
    public void sendMessage(MessageTypeEnum messageType, Table table, Integer receiver){
        User user = (User)WebUtil.getSessionKey(SessionKey.USER);
        Message message = new Message();
        message.setReceiver(receiver);
        if(messageType == MessageTypeEnum.COMMENT){
            // 自己评论自己的表则不发消息
            if(!table.getOwner().equals(user.getId())){
                message.setType(messageType.getCode());
                message.setContent(user.getNickname() + " 评论了您的收集表 [ " + table.getName() + " ]");
            }
        }else if(messageType == MessageTypeEnum.REPLY){
            message.setType(messageType.getCode());
            message.setContent(user.getNickname() + " 回复了您的评论 - [ " + table.getName() + " ]");
        }else if(messageType == MessageTypeEnum.TABLE_FILLED){
            message.setType(MessageTypeEnum.SYSTEM.getCode());
            message.setContent(user.getNickname() + " 参与了您的收集表 [ " + table.getName() + " ]");
        }else if(messageType == MessageTypeEnum.TABLE_END){
            message.setType(MessageTypeEnum.SYSTEM.getCode());
            message.setContent("您的收集表 [ " + table.getName() + " ] 到达截至时间，已停止收集");
        }else {
            // 在这里添加更多类型的消息的支持
            throw new RuntimeException("还没添加这个类型的解析，快来完善");
        }
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
        //message.setId(UUID.randomUUID().toString());
        message.setRead(false);
        message.setDelete(false);
        message.setCreateTime(new Date());
        MessageQueue.offer(message);
    }
}

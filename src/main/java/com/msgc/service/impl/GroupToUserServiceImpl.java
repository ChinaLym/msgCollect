package com.msgc.service.impl;

import com.msgc.entity.Group;
import com.msgc.entity.GroupToUser;
import com.msgc.entity.User;
import com.msgc.repository.IGroupToUserRepository;
import com.msgc.service.IGroupToUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* Type: GroupToUserServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class GroupToUserServiceImpl implements IGroupToUserService{

    private IGroupToUserRepository groupToUserRepository;
    
    @Autowired
    public void setGroupToUserRepositry(IGroupToUserRepository grouptouserRepositry) {
        this.groupToUserRepository = grouptouserRepositry;
    }

    /**
     *  添加 组 —— 用户 对应关系（用户加入组）
     * @param user  关联的用户
     * @param group 关联的组
     * @return  添加后的关系实体
     */
    @Override
    public GroupToUser newRelationShip(User user, Group group) {
        GroupToUser relationship = new GroupToUser();
        relationship.setUserId(user.getId());
        relationship.setGroupId(group.getId());
        relationship.setGroupNick(user.getNickname());
        relationship.setCreateTime(new Date());
        return groupToUserRepository.save(relationship);
    }
}

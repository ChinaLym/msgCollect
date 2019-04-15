package com.msgc.service;

import com.msgc.entity.Group;
import com.msgc.entity.GroupToUser;
import com.msgc.entity.User;

public interface IGroupToUserService {

    GroupToUser newRelationShip(User user, Group group);
}

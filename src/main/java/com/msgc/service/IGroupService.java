package com.msgc.service;

import com.msgc.entity.Group;

import java.util.List;

public interface IGroupService {

    Group save(Group group);

    Group findById(Integer id);
    
    List<Group> findAllById(List<Integer> idList);
    
    List<Group> findAll(Group groupExample);
    
    List<Group> findAll();
    
    void deleteById(Integer id);
    
    List<Group> save(List<Group> groupList);

    Group createNewGroup(Group group);

    List<Group> findByMasterId(Integer masterId);
}

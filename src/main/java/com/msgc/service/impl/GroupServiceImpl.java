package com.msgc.service.impl;

import com.msgc.entity.Group;
import com.msgc.repository.IGroupRepository;
import com.msgc.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
* Type: GroupServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class GroupServiceImpl implements IGroupService{

    private final IGroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(IGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }
    
    @Override
    public Group findById(Integer id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.orElse(null);
    }
    
    @Override
    public List<Group> findAllById(List<Integer> idList) {
        return groupRepository.findAllById(idList);
    }
    
    @Override
    public List<Group> findAll(Group groupExample) {
        return groupRepository.findAll(Example.of(groupExample));
    }
    
    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
	public void deleteById(Integer id) {
		groupRepository.deleteById(id);
	}
    
    @Override
    public List<Group> save(List<Group> groupList) {
        return groupRepository.saveAll(groupList);
    }

    //上限，每个人创建10个组
    @Override
    public Group createNewGroup(Group group){
        return groupRepository.createNewGroup(group);
    }

    @Override
    public List<Group> findByMasterId(Integer masterId) {
        return groupRepository.findByMasterId(masterId);
    }
}

package com.msgc.service;

import com.msgc.entity.User;

import java.util.List;

public interface IUserService {

    User login(User user);

    User register(User user);

    List<User> findAll();

    List<User> findListByIds(List<Integer> ids);

    User findById(Integer id);

    User save(User user);

    List<User> save(List<User> userList);

    void delete(Integer id);

}

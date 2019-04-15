package com.msgc.service.impl;

import com.msgc.entity.User;
import com.msgc.repository.IUserRepository;
import com.msgc.service.IUserService;
import com.msgc.utils.RandomHeadImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

/**
 * Type: UserServiceImpl
 * Description: serviceImp
 *
 * @author LYM
 */
@Service
public class UserServiceImpl implements IUserService {

    private IUserRepository userRepository;

    @Autowired
    public void setUserRepositry(IUserRepository userRepositry) {
        this.userRepository = userRepositry;
    }

    @Override
    public User login(User user) {
        return userRepository.findByAccountAndPassword(user.getAccount(), user.getPassword());
    }

    @Override
    public User register(User user) {
        if(userRepository.countByAccount(user.getAccount()) > 0)
            return null;
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (StringUtils.isBlank(user.getNickname())) {
            user.setNickname("用户" + new Random().nextInt());
        }
        if(StringUtils.isBlank(user.getHeadImage())){
            user.setHeadImage(RandomHeadImageUtil.next());
        }
        user = userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findListByIds(List<Integer> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> save(List<User> userList) {
        return userRepository.saveAll(userList);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}

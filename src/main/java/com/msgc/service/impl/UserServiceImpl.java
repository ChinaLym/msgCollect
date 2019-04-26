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
import java.util.UUID;

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
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 登录
     * @param user 前端传来的参数
     * @return 数据库中的 user
     */
    @Override
    public User login(User user) {
        return userRepository.findByAccountAndPassword(user.getAccount(), user.getPassword());
    }

    /**注册。
     * @param user 前端传来的参数
     * @return 保存数据库后的user，若为 null 则注册失败
     */
    @Override
    public User register(User user) {
        //TODO 这里需要将判断账号是否已经存在放到插入中
        if(userRepository.countByAccount(user.getAccount()) > 0)
            return null;
        user.setHeadImage(RandomHeadImageUtil.next());
        user.setNickname("用户" + UUID.randomUUID().toString().substring(0, 10));
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

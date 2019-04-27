package com.msgc.service.impl;

import com.msgc.entity.UserCookie;
import com.msgc.repository.IUserCookieRepository;
import com.msgc.service.IUserCookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
* Type: UserCookieServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class UserCookieServiceImpl implements IUserCookieService{

    private final IUserCookieRepository userCookieRepository;

    @Autowired
    public UserCookieServiceImpl(IUserCookieRepository userCookieRepository) {
        this.userCookieRepository = userCookieRepository;
    }

    @Override
    public UserCookie save(UserCookie userCookie) {
        return userCookieRepository.save(userCookie);
    }
    
    @Override
    public UserCookie findByCookie(String cookie) {
        return userCookieRepository.findByCookie(cookie);
    }

    @Override
    public void deleteByCookie(String cookie) {
        userCookieRepository.deleteByCookie(cookie);
    }
}

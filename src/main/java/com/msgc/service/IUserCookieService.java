package com.msgc.service;

import com.msgc.entity.UserCookie;

import java.util.List;

public interface IUserCookieService {

    UserCookie save(UserCookie userCookie);

    UserCookie findById(Integer id);
    
    List<UserCookie> findAllById(List<Integer> idList);
    
    List<UserCookie> findAll(UserCookie userCookieExample);
    
    List<UserCookie> findAll();
    
    void deleteById(Integer id);
    
    List<UserCookie> save(List<UserCookie> userCookieList);

    UserCookie findByCookie(String cookie);

    void deleteByCookie(String cookie);
}

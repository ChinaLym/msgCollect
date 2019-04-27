package com.msgc.service;

import com.msgc.entity.UserCookie;

public interface IUserCookieService {

    UserCookie save(UserCookie userCookie);

    UserCookie findByCookie(String cookie);

    void deleteByCookie(String cookie);
}

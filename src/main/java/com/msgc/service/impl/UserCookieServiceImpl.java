package com.msgc.service.impl;

import com.msgc.entity.UserCookie;
import com.msgc.repository.IUserCookieRepository;
import com.msgc.service.IUserCookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
* Type: UserCookieServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class UserCookieServiceImpl implements IUserCookieService{

    @Autowired
    private IUserCookieRepository userCookieRepository;    
    
    @Override
    public UserCookie save(UserCookie userCookie) {
        return userCookieRepository.save(userCookie);
    }
    
    @Override
    public UserCookie findById(Integer id) {
        Optional<UserCookie> userCookie = userCookieRepository.findById(id);
        return userCookie.orElse(null);
    }
    
    @Override
    public List<UserCookie> findAllById(List<Integer> idList) {
        return userCookieRepository.findAllById(idList);
    }
    
    @Override
    public List<UserCookie> findAll(UserCookie userCookieExample) {
        return userCookieRepository.findAll(Example.of(userCookieExample));
    }
    
    @Override
    public List<UserCookie> findAll() {
        return userCookieRepository.findAll();
    }

    @Override
	public void deleteById(Integer id) {
		userCookieRepository.deleteById(id);
	}
    
    @Override
    public List<UserCookie> save(List<UserCookie> userCookieList) {
        return userCookieRepository.saveAll(userCookieList);
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

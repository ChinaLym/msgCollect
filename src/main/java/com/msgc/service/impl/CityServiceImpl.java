package com.msgc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgc.entity.City;
import com.msgc.repository.ICityRepository;
import com.msgc.service.ICityService;

/**
* Type: CityServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class CityServiceImpl implements ICityService{

    private ICityRepository cityRepository;
    
    @Autowired
    public void setCityRepositry(ICityRepository cityRepositry) {
        this.cityRepository = cityRepositry;
    }
    
    
}

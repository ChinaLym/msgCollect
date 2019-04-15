package com.msgc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgc.entity.Province;
import com.msgc.repository.IProvinceRepository;
import com.msgc.service.IProvinceService;

/**
* Type: ProvinceServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class ProvinceServiceImpl implements IProvinceService{

    private IProvinceRepository provinceRepository;
    
    @Autowired
    public void setProvinceRepositry(IProvinceRepository provinceRepositry) {
        this.provinceRepository = provinceRepositry;
    }
    
    
}

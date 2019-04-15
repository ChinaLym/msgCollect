package com.msgc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgc.entity.Area;
import com.msgc.repository.IAreaRepository;
import com.msgc.service.IAreaService;

/**
* Type: AreaServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class AreaServiceImpl implements IAreaService{

    private IAreaRepository areaRepository;
    
    @Autowired
    public void setAreaRepositry(IAreaRepository areaRepositry) {
        this.areaRepository = areaRepositry;
    }
    
    
}

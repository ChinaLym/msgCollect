package com.msgc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgc.entity.Admin;
import com.msgc.repository.IAdminRepository;
import com.msgc.service.IAdminService;

/**
* Type: AdminServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class AdminServiceImpl implements IAdminService{

    private IAdminRepository adminRepository;
    
    @Autowired
    public void setAdminRepositry(IAdminRepository adminRepositry) {
        this.adminRepository = adminRepositry;
    }
    
    
}

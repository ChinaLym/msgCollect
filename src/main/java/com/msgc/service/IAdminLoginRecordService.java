package com.msgc.service;

import com.msgc.entity.AdminLoginRecord;

import java.util.List;

public interface IAdminLoginRecordService {

    AdminLoginRecord save(AdminLoginRecord adminloginrecord);

    AdminLoginRecord findById(Integer id);
    
    List<AdminLoginRecord> findAllById(List<Integer> ids);
    
    List<AdminLoginRecord> findAll();
    
    void deleteById(Integer id);
    
    List<AdminLoginRecord> save(List<AdminLoginRecord> adminloginrecordList);
}

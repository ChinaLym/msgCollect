package com.msgc.service.impl;

import com.msgc.entity.AdminLoginRecord;
import com.msgc.repository.IAdminLoginRecordRepository;
import com.msgc.service.IAdminLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
/**
* Type: AdminLoginRecordServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class AdminLoginRecordServiceImpl implements IAdminLoginRecordService{
    private final IAdminLoginRecordRepository adminloginrecordRepository;

    @Autowired
    public AdminLoginRecordServiceImpl(IAdminLoginRecordRepository adminloginrecordRepository) {
        this.adminloginrecordRepository = adminloginrecordRepository;
    }


    @Override
    public AdminLoginRecord save(AdminLoginRecord adminloginrecord) {
        return adminloginrecordRepository.save(adminloginrecord);
    }
    
    @Override
    public AdminLoginRecord findById(Integer id) {
        Optional<AdminLoginRecord> adminloginrecord = adminloginrecordRepository.findById(id);
        return adminloginrecord.orElse(null);
    }
    
    @Override
    public List<AdminLoginRecord> findAllById(List<Integer> ids) {
        return adminloginrecordRepository.findAllById(ids);
    }
    
    @Override
    public List<AdminLoginRecord> findAll() {
        return adminloginrecordRepository.findAll();
    }

    @Override
	public void deleteById(Integer id) {
		adminloginrecordRepository.deleteById(id);
	}
    
    @Override
    public List<AdminLoginRecord> save(List<AdminLoginRecord> adminloginrecordList) {
        return adminloginrecordRepository.saveAll(adminloginrecordList);
    }
}

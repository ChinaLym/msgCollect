package com.msgc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgc.entity.TableGroupWhiteList;
import com.msgc.repository.ITableGroupWhiteListRepository;
import com.msgc.service.ITableGroupWhiteListService;

/**
* Type: TableGroupWhiteListServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class TableGroupWhiteListServiceImpl implements ITableGroupWhiteListService{

    private ITableGroupWhiteListRepository tablegroupwhitelistRepository;
    
    @Autowired
    public void setTableGroupWhiteListRepositry(ITableGroupWhiteListRepository tablegroupwhitelistRepositry) {
        this.tablegroupwhitelistRepository = tablegroupwhitelistRepositry;
    }
    
    
}

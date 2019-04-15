package com.msgc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgc.entity.FieldType;
import com.msgc.repository.IFieldTypeRepository;
import com.msgc.service.IFieldTypeService;

import java.util.List;

/**
* Type: FieldTypeServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class FieldTypeServiceImpl implements IFieldTypeService{

    private IFieldTypeRepository fieldtypeRepository;
    
    @Autowired
    public void setFieldTypeRepositry(IFieldTypeRepository fieldtypeRepositry) {
        this.fieldtypeRepository = fieldtypeRepositry;
    }


    @Override
    public List<FieldType> findAll() {
        return fieldtypeRepository.findAll();
    }
}

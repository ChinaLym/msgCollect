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

    private IFieldTypeRepository fieldTypeRepository;
    
    @Autowired
    public void setFieldTypeRepository(IFieldTypeRepository fieldTypeRepository) {
        this.fieldTypeRepository = fieldTypeRepository;
    }


    @Override
    public List<FieldType> findAll() {
        return fieldTypeRepository.findAll();
    }
}

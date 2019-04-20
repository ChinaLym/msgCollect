package com.msgc.service.impl;

import com.msgc.entity.Field;
import com.msgc.repository.IFieldRepository;
import com.msgc.service.IFieldService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* Type: FieldServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class FieldServiceImpl implements IFieldService{

    private IFieldRepository fieldRepository;
    
    @Autowired
    public void setFieldRepository(IFieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }


    @Override
    public List<Field> save(List<Field> fieldList) {
        if(CollectionUtils.isNotEmpty(fieldList))
            return fieldRepository.saveAll(fieldList);
        else throw new NullPointerException();
    }

    @Override
    public List<Field> findAll(Field example) {
        return fieldRepository.findAll(Example.of(example));
    }

    @Override
    public Field findById(int fieldId) {
        return fieldRepository.findById(fieldId).orElse(null);
    }

    @Override
    public Boolean deleteByTableId(int tableId) {
        return fieldRepository.deleteByTableId(tableId) > 0;
    }

    @Override
    public List<Field> findByTableId(int tableId) {
        Field field = new Field();
        field.setTableId(tableId);
        return fieldRepository.findAll(field);
    }

}

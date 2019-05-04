package com.msgc.service.impl;

import com.msgc.entity.Field;
import com.msgc.repository.IFieldRepository;
import com.msgc.service.IFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* Type: FieldServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
@CacheConfig(cacheNames = "fieldCache")//缓存key: tableId value: List<Field>
public class FieldServiceImpl implements IFieldService{

    private IFieldRepository fieldRepository;
    
    @Autowired
    public FieldServiceImpl(IFieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @CacheEvict(allEntries = true)
    @Override
    public List<Field> save(List<Field> fieldList) {
            return fieldRepository.saveAll(fieldList);
    }

    @Cacheable(key = "'id' + #fieldId")
    @Override
    public Field findById(Integer fieldId) {
        return fieldRepository.findById(fieldId).orElse(null);
    }

    @CacheEvict(allEntries = true)
    @Override
    public Boolean deleteByTableId(Integer tableId) {
        return fieldRepository.deleteByTableId(tableId) > 0;
    }

    @Cacheable(key = "'t' + #tableId")
    @Override
    public List<Field> findAllByTableId(Integer tableId) {
        return fieldRepository.findByTableId(tableId);
    }

}

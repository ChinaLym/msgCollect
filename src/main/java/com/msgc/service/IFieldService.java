package com.msgc.service;

import com.msgc.entity.Field;

import java.util.List;

public interface IFieldService {

    List<Field> save(List<Field> fieldList);

    List<Field> findAllByTableId(Integer tableId);

    Field findById(Integer fieldId);

    Boolean deleteByTableId(Integer tableId);

}

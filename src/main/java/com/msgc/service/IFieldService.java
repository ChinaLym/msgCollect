package com.msgc.service;

import com.msgc.entity.Field;

import java.util.List;

public interface IFieldService {

    List<Field> save(List fieldList);

    List<Field> findAll(Field example);

    Field findById(int fieldId);

    Boolean deleteByTableId(int tableId);

    List<Field> findByTableId(int tableId);
}

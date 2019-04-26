package com.msgc.repository;

import com.msgc.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* Description: DAO. 
*   Deal directly with the database, 
*   if you customize the query, take the index first
* 
* 		Please follow the prefix convention below
*
*   getOne ------------ getXXX
*   getMultiple ------- listXXX
*   count ------------- countXXX
*   getOne ------------ getXXX
*   insert ------------ saveXXX
*   delete ------------ deleteXXX
*   modify ------------ updateXXX
*
*/
public interface IFieldRepository extends JpaRepository<Field, Integer> {

    @Transactional
    @Modifying
    @Query("delete Field where tableId=?1")
    Integer deleteByTableId(int tableId);

    List<Field> findAll(Field field);

    List<Field> findByTableId(Integer tableId);
}

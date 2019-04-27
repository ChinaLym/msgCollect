package com.msgc.repository;

import com.msgc.entity.Table;
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
public interface ITableRepository extends JpaRepository<Table, Integer> {
    @Transactional
    @Modifying
    @Query("update Table set state = 'DELETE' where id=?1 and owner=?2")
    Integer deleteByIdAndOwner(Integer tableId, Integer owner);


    @Modifying
    @Query("update Table set filledNum = filledNum+1 where id=?1")
    void increaseFilledNum(Integer tableId);

    @Transactional
    @Modifying
    @Query("update Table set state = 'END' where id=?1 and owner=?2")
    Integer stopByIdAndOwner(Integer tableId, Integer owner);

    List<Table> findAllByOwnerAndStateNot(Integer owner, String value);

    Table findByIdAndOwner(int tableId, int userId);

    //由于 tableName 未使用全文索引，所以将 State 放在前，优化查询
    List<Table> findAllByStateAndNameContaining(String value, String tableName);
}

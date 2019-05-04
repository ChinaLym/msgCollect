package com.msgc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msgc.entity.UnfilledRecord;

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
public interface IUnfilledRecordRepository extends JpaRepository<UnfilledRecord, Integer> {


    List<UnfilledRecord> findByUserIdAndTableId(Integer id, Integer tableId);

    void deleteAllByTableId(List<Integer> tableIdList);

    List<UnfilledRecord> findAllByUserIdAndFilledAndDelete(Integer userId, boolean b, boolean b1);
}

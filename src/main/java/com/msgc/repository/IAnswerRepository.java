package com.msgc.repository;

import com.msgc.entity.Answer;
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
public interface IAnswerRepository extends JpaRepository<Answer, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Answer where answerRecordId=?1")
    void deleteAllByRecordId(int answerRecordId);

    List<Answer> findByFieldIdIn(List<Integer> fieldIdList);

    List<Answer> findAllByAnswerRecordId(Integer recordId);
}

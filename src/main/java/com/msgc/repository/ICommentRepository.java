package com.msgc.repository;

import com.msgc.entity.Comment;
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
public interface ICommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByTableIdAndEffective(Integer id, boolean isEffective);

    @Transactional
    @Modifying
    @Query("update Comment set effective=false where id=?1 and effective=true")
    Integer setDeleteStateById(Integer id);
}

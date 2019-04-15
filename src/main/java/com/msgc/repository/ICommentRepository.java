package com.msgc.repository;

import com.msgc.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

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
}

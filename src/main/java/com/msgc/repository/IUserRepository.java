package com.msgc.repository;

import com.msgc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface IUserRepository extends JpaRepository<User, Integer> {

    User findByAccountAndPassword(String account, String password);

    int countByAccount(String account);
}

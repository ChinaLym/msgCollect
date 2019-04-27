package com.msgc.repository;

import com.msgc.entity.Message;
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
public interface IMessageRepository extends JpaRepository<Message, String> {

    @Transactional
    @Modifying
    @Query(value = "update tb_message set is_delete=true where id=?1 and receiver=?2 and is_delete=false", nativeQuery = true)
    void setDeleteStateById(String id, Integer userId);

    @Transactional
    @Modifying
    @Query(value = "update tb_message set is_delete=true where receiver=?2 and type=?1 and is_delete=false", nativeQuery = true)
    void setDeleteStateByType(Integer typeCode, Integer userId);

    @Transactional
    @Modifying
    @Query(value = "update tb_message set is_read=true where id=?1 and receiver=?2 and is_read=false", nativeQuery = true)
    void setReadStateById(String messageId, Integer userId);

    @Transactional
    @Modifying
    @Query(value = "update tb_message set is_read=true where receiver=?2 and type=?1 and is_read=false", nativeQuery = true)
    void setAllReadStateByType(Integer messageType, Integer userId);

    @Query(value = "select * from tb_message where receiver=?1 and is_read=false limit ?2", nativeQuery = true)
    List<Message> findUnreadByReceiverAndLimit(Integer receiver, int limitNum);

    List<Message> findAllByReceiverAndTypeAndDelete(Integer receiverId, Integer type, boolean b);
}

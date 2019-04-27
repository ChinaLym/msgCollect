package com.msgc.repository;

import com.msgc.entity.Group;
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
public interface IGroupRepository extends JpaRepository<Group, Integer> {

    //上限，每个人创建10个组
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tb_group ( " +
            "  name, " +
            "  master_id, " +
            "  icon, " +
            "  member_number, " +
            "  create_time " +
            ")  " +
            "SELECT  " +
            "  #{newGroup.name}, #{newGroup.masterId}, #{newGroup.icon}, #{newGroup.memberNumber}, #{newGroup.createTime}" +
            " FROM DUAL WHERE NOT EXISTS  " +
            "  (SELECT  " +
            "    1 " +
            "  FROM " +
            "    (SELECT " +
            "      COUNT(1) AS temp_num  " +
            "    FROM " +
            "      tb_group tempTb  " +
            "    WHERE tempTb.master_id = ?2) a WHERE a.temp_num > 3)"
            ,nativeQuery = true)
    Group createNewGroup(Group newGroup);

    List<Group> findByMasterId(Integer masterId);
}

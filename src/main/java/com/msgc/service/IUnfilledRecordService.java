
package com.msgc.service;

import com.msgc.entity.UnfilledRecord;

import java.util.List;

/**
 * UnfilledRecord业务逻辑接口
 */
public interface IUnfilledRecordService {

    UnfilledRecord save(UnfilledRecord unfilledTable);

    UnfilledRecord findById(Integer id);
    
    List<UnfilledRecord> findAllById(List<Integer> idList);
    
    List<UnfilledRecord> findAll(UnfilledRecord unfilledTableExample);
    
    List<UnfilledRecord> findAll();
    
    void deleteById(Integer id);
    
    List<UnfilledRecord> save(List<UnfilledRecord> unfilledTableList);

    List<UnfilledRecord> findAllByUserId(Integer userId);

    void deleteByTableIds(List<Integer> tableIdList);

    UnfilledRecord findByUserIdAndTableId(Integer userId, Integer tableId);
}
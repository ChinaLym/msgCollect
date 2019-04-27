
package com.msgc.service;

import com.msgc.entity.UnfilledRecord;

import java.util.List;

/**
 * UnfilledRecord业务逻辑接口
 */
public interface IUnfilledRecordService {

    UnfilledRecord save(UnfilledRecord unfilledTable);

    List<UnfilledRecord> findAllByUserId(Integer userId);

    void deleteByTableIds(List<Integer> tableIdList);

    UnfilledRecord findByUserIdAndTableId(Integer userId, Integer tableId);
}
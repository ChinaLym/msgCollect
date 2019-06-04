package com.msgc.service.impl;

import com.msgc.entity.UnfilledRecord;
import com.msgc.repository.IUnfilledRecordRepository;
import com.msgc.service.IUnfilledRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
/**
* Type: UnfilledTableServiceImpl
* Description: 业务逻辑实现类
* @author LYM
 */
@Service
@CacheConfig(cacheNames = "unfilledRecord")
public class UnfilledRecordServiceImpl implements IUnfilledRecordService {

    private final IUnfilledRecordRepository unfilledRecordRepository;

    @Autowired
    public UnfilledRecordServiceImpl(IUnfilledRecordRepository unfilledRecordRepository) {
        this.unfilledRecordRepository = unfilledRecordRepository;
    }

    @Override
    @Caching(evict={
            @CacheEvict(key = "'u' + #unfilledTable.userId"),
            @CacheEvict(key = "'u' + #unfilledTable.userId + 't' + #unfilledTable.tableId")
    })
    public UnfilledRecord save(UnfilledRecord unfilledTable) {
        return unfilledRecordRepository.save(unfilledTable);
    }

    /**
     * 找出所有用户可以填写的表
     * @param userId 用户 id
     * @return 收藏记录列表
     */
    @Override
    @Cacheable(key = "'u' + #userId")
    public List<UnfilledRecord> findAllByUserId(Integer userId) {
        return unfilledRecordRepository.findAllByUserIdAndFilledAndDelete(userId, false, false);
    }

    /**
     * 删除所有 收集表id 的收藏记录，如当一个表截至或删除时应当删掉所有收藏该表的记录
     * @param tableIdList 收集表id 
     */
    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteByTableIds(List<Integer> tableIdList) {
        unfilledRecordRepository.deleteAllByTableId(tableIdList);
    }

    /**
     * 找出指定userId，tableId 的所有收藏记录
     * @param userId 用户id
     * @param tableId   收藏表id
     * @return  一条收藏记录
     */
    @Override
    @Cacheable(key = "'u' + #userId + 't' + #tableId")
    public UnfilledRecord findByUserIdAndTableId(Integer userId, Integer tableId) {
        List<UnfilledRecord> list = unfilledRecordRepository.findByUserIdAndTableId(userId, tableId);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}
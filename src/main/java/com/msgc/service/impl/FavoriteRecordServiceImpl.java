package com.msgc.service.impl;

import com.msgc.entity.FavoriteRecord;
import com.msgc.repository.IFavoriteRecordRepository;
import com.msgc.service.IFavoriteRecordService;
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
* Type: FavoriteRecordServiceImpl
* Description: 业务逻辑实现类
* @author LYM
 */
@Service
@CacheConfig(cacheNames = "favoriteRecord")
public class FavoriteRecordServiceImpl implements IFavoriteRecordService {

    private final IFavoriteRecordRepository favoriteRecordRepository;

    @Autowired
    public FavoriteRecordServiceImpl(IFavoriteRecordRepository favoriteRecordRepository) {
        this.favoriteRecordRepository = favoriteRecordRepository;
    }

    /**
     * 保存或更新
     * @param unfilledTable 要存数据库的实体
     * @return 保存后的
     */
    @Override
    @Caching(evict={
            @CacheEvict(key = "'u' + #unfilledTable.userId"),
            @CacheEvict(key = "'u' + #unfilledTable.userId + 't' + #unfilledTable.tableId")
    })
    public FavoriteRecord save(FavoriteRecord unfilledTable) {
        return favoriteRecordRepository.save(unfilledTable);
    }

    /**
     * 找出所有用户可以填写的表
     * @param userId 用户 id
     * @return 收藏记录列表
     */
    @Override
    @Cacheable(key = "'u' + #userId")
    public List<FavoriteRecord> findAllByUserId(Integer userId) {
        return favoriteRecordRepository.findAllByUserIdAndFilledAndDelete(userId, false, false);
    }

    /**
     * 删除所有 收集表id 的收藏记录，如当一个表截至或删除时应当删掉所有收藏该表的记录
     * @param tableIdList 收集表id 
     */
    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteByTableIds(List<Integer> tableIdList) {
        favoriteRecordRepository.deleteAllByTableId(tableIdList);
    }

    /**
     * 找出指定userId，tableId 的所有收藏记录
     * @param userId 用户id
     * @param tableId   收藏表id
     * @return  一条收藏记录
     */
    @Override
    @Cacheable(key = "'u' + #userId + 't' + #tableId")
    public FavoriteRecord findByUserIdAndTableId(Integer userId, Integer tableId) {
        List<FavoriteRecord> list = favoriteRecordRepository.findByUserIdAndTableId(userId, tableId);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    @Transactional
    @CacheEvict(key = "'u' + #userId + 't' + #tableId")
    public void unLikeTable(Integer userId, Integer tableId) {
        FavoriteRecord favoriteRecord = this.findByUserIdAndTableId(userId, tableId);
        if(favoriteRecord == null)
            return;
        favoriteRecord.setDelete(true);
        this.save(favoriteRecord);
    }
}
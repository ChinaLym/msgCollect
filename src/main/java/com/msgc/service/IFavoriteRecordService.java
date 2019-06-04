
package com.msgc.service;

import com.msgc.entity.FavoriteRecord;

import java.util.List;

/**
 * IFavoriteRecordService 业务逻辑接口
 */
public interface IFavoriteRecordService {

    FavoriteRecord save(FavoriteRecord favoriteRecord);

    List<FavoriteRecord> findAllByUserId(Integer userId);

    void deleteByTableIds(List<Integer> tableIdList);

    FavoriteRecord findByUserIdAndTableId(Integer userId, Integer tableId);

    void unLikeTable(Integer userId, Integer tableId);
}
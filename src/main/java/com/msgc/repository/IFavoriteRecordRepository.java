package com.msgc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msgc.entity.FavoriteRecord;

import java.util.List;


public interface IFavoriteRecordRepository extends JpaRepository<FavoriteRecord, Integer> {


    List<FavoriteRecord> findByUserIdAndTableId(Integer id, Integer tableId);

    void deleteAllByTableId(List<Integer> tableIdList);

    List<FavoriteRecord> findAllByUserIdAndFilledAndDelete(Integer userId, boolean isFilled, boolean isDelete);
}

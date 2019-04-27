package com.msgc.service.impl;

import com.msgc.entity.AnswerRecord;
import com.msgc.repository.IAnswerRecordRepository;
import com.msgc.service.IAnswerRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
/**
* Type: AnswerRecordServiceImpl
* Description: 保存查找 用户-收集表 填写记录，带缓存
* @author LYM
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "answerRecordCache")
public class AnswerRecordServiceImpl implements IAnswerRecordService{

    private IAnswerRecordRepository answerRecordRepository;

    @Autowired
    public void setAnswerRecordRepository(IAnswerRecordRepository answerRecordRepository) {
        this.answerRecordRepository = answerRecordRepository;
    }


    @Caching(evict={
            @CacheEvict(key="'t' + #answerRecord.tableId"),
            @CacheEvict(key="'u' + #answerRecord.userId")})
    @Override
    public AnswerRecord save(AnswerRecord answerRecord) {
        return answerRecordRepository.save(answerRecord);
    }

    @Cacheable(key = "'t' + #tableId")
    @Override
    public List<AnswerRecord> findAllByTableId(Integer tableId) {
        return answerRecordRepository.findAllByTableId(tableId);
    }

    @Cacheable(key = "'u' + #userId")
    @Override
    public List<AnswerRecord> findAllByUserId(Integer userId) {
        return answerRecordRepository.findAllByUserId(userId);
    }

    @Cacheable(key = "'t' + #tableId + 'u' + #userId")
    @Override
    public AnswerRecord findByTableIdAndUserId(Integer tableId, Integer userId) {
        List<AnswerRecord> records =  answerRecordRepository.findAllByTableIdAndUserId(tableId, userId);
        if(CollectionUtils.isEmpty(records))
            return null;
        else if(records.size() < 2){
            return records.get(0);
        }
        log.error("多个 用户-表 填写记录！tableId=" + tableId + "  userId=" + userId);
        throw new IllegalStateException("多个 用户-表 填写记录！");

    }
}

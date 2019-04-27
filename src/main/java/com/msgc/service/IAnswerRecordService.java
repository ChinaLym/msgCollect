package com.msgc.service;

import com.msgc.entity.AnswerRecord;

import java.util.List;

public interface IAnswerRecordService {

    AnswerRecord save(AnswerRecord answerrecord);

    List<AnswerRecord> findAllByTableId(Integer tableId);

    List<AnswerRecord> findAllByUserId(Integer userId);

    List<AnswerRecord> findAllByTableIdAndUserId(Integer userId, Integer tableId);

    void deleteById(Integer id);
}

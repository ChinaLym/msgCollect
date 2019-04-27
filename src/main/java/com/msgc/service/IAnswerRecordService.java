package com.msgc.service;

import com.msgc.entity.AnswerRecord;

import java.util.List;

public interface IAnswerRecordService {

    AnswerRecord save(AnswerRecord answerrecord);

    List<AnswerRecord> findAllByTableId(Integer tableId);

    List<AnswerRecord> findAllByUserId(Integer userId);

    AnswerRecord findByTableIdAndUserId(Integer userId, Integer tableId);
}

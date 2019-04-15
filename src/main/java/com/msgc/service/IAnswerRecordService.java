package com.msgc.service;

import com.msgc.entity.AnswerRecord;

import java.util.List;

public interface IAnswerRecordService {

    AnswerRecord save(AnswerRecord answerrecord);

    AnswerRecord findById(Integer id);
    
    List<AnswerRecord> findAllById(List<Integer> ids);
    
    List<AnswerRecord> findAll(AnswerRecord example);
    
    void deleteById(Integer id);
}

package com.msgc.service;

import com.msgc.entity.Answer;

import java.util.List;

public interface IAnswerService {

    Answer findById(Integer id);
    
    List<Answer> findAllByRecordId(Integer recordId);
    
    List<Answer> save(List<Answer> answersList);

    void deleteAllByRecordId(Integer answerRecordId);

    List<Answer> findAllByFieldIds(List<Integer> fieldIdList);
}

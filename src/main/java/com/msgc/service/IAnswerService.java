package com.msgc.service;

import com.msgc.entity.Answer;

import java.util.List;

public interface IAnswerService {

    Answer findById(Integer id);
    
    List<Answer> findAll(Answer example);
    
    List<Answer> save(List<Answer> answersList);

    void deleteAllByRecordId(int answerRecordId);

    List<Answer> findAllByFieldIds(List<Integer> fieldIdList);
}

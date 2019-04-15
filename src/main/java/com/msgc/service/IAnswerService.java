package com.msgc.service;

import com.msgc.entity.Answer;

import java.util.List;

public interface IAnswerService {

    Answer save(Answer answer);

    Answer findById(Integer id);
    
    List<Answer> findAllById(List<Integer> ids);
    
    List<Answer> findAll();

    List<Answer> findAll(Answer example);
    
    void deleteById(Integer id);

    List<Answer> save(List<Answer> answersList);

    void deleteAllByRecordId(int answerRecordId);

    List<Answer> findAllByFieldIds(List<Integer> fieldIdList);
}

package com.msgc.service.impl;

import com.msgc.entity.AnswerRecord;
import com.msgc.repository.IAnswerRecordRepository;
import com.msgc.service.IAnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
/**
* Type: AnswerRecordServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class AnswerRecordServiceImpl implements IAnswerRecordService{

    private IAnswerRecordRepository answerRecordRepository;
    
    @Autowired
    public void setAnswerRecordRepository(IAnswerRecordRepository answerRecordRepository) {
        this.answerRecordRepository = answerRecordRepository;
    }
    
    
    @Override
    public AnswerRecord save(AnswerRecord answerRecord) {
        return answerRecordRepository.save(answerRecord);
    }

    @Override
    public List<AnswerRecord> findAllByTableId(Integer tableId) {
        return answerRecordRepository.findAllByTableId(tableId);
    }

    @Override
    public List<AnswerRecord> findAllByUserId(Integer userId) {
        return answerRecordRepository.findAllByUserId(userId);
    }

    @Override
    public List<AnswerRecord> findAllByTableIdAndUserId(Integer tableId, Integer userId) {
        return answerRecordRepository.findAllByTableIdAndUserId(tableId, userId);
    }

    @Override
	public void deleteById(Integer id) {
		answerRecordRepository.deleteById(id);
	}
    
}

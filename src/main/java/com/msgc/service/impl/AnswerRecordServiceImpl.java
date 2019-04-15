package com.msgc.service.impl;

import com.msgc.entity.AnswerRecord;
import com.msgc.repository.IAnswerRecordRepository;
import com.msgc.service.IAnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
/**
* Type: AnswerRecordServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class AnswerRecordServiceImpl implements IAnswerRecordService{

    private IAnswerRecordRepository answerrecordRepository;
    
    @Autowired
    public void setAnswerRecordRepositry(IAnswerRecordRepository answerrecordRepositry) {
        this.answerrecordRepository = answerrecordRepositry;
    }
    
    
    @Override
    public AnswerRecord save(AnswerRecord answerrecord) {
        return answerrecordRepository.save(answerrecord);
    }
    
    @Override
    public AnswerRecord findById(Integer id) {
        Optional<AnswerRecord> answerrecord = answerrecordRepository.findById(id);
        return answerrecord.isPresent() ? answerrecord.get() : null;
    }
    
    @Override
    public List<AnswerRecord> findAllById(List<Integer> ids) {
        return answerrecordRepository.findAllById(ids);
    }

    @Override
    public List<AnswerRecord> findAll(AnswerRecord example) {
        return answerrecordRepository.findAll(Example.of(example));
    }

    @Override
	public void deleteById(Integer id) {
		answerrecordRepository.deleteById(id);
	}
    
}

package com.msgc.service.impl;

import com.msgc.entity.Answer;
import com.msgc.repository.IAnswerRepository;
import com.msgc.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
/**
* Type: AnswerServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class AnswerServiceImpl implements IAnswerService{

    private IAnswerRepository answerRepository;
    
    @Autowired
    public void setAnswerRepositry(IAnswerRepository answerRepositry) {
        this.answerRepository = answerRepositry;
    }
    
    
    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }
    
    @Override
    public Answer findById(Integer id) {
        Optional<Answer> answer = answerRepository.findById(id);
        return answer.isPresent() ? answer.get() : null;
    }
    
    @Override
    public List<Answer> findAllById(List<Integer> ids) {
        return answerRepository.findAllById(ids);
    }
    
    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public List<Answer> findAll(Answer example) {
        return answerRepository.findAll(Example.of(example));
    }

    @Override
	public void deleteById(Integer id) {
		answerRepository.deleteById(id);
	}

    @Override
    public List<Answer> save(List<Answer> answersList) {
        return answerRepository.saveAll(answersList);
    }

    //删除时如果 type 是文件 也要把原来文件删掉
    @Override
    public void deleteAllByRecordId(int answerRecordId) {
        Answer answerExample = new Answer();
        answerExample.setAnswerRecordId(answerRecordId);
        List<Answer> answerList = answerRepository.findAll(Example.of(answerExample));
        answerList.stream()
                .filter(answer -> "文件".equals(answer.getType()))
                .forEach(answer -> {
                    //删除之前的文件
                    File oldFile = new File(answer.getContent());
                    oldFile.delete();
                });
        answerRepository.deleteAllByRecordId(answerRecordId);
    }

    @Override
    public List<Answer> findAllByFieldIds(List<Integer> fieldIdList){
        return answerRepository.findByFieldIdIn(fieldIdList);
    }

}

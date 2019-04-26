package com.msgc.service.impl;

import com.msgc.entity.Answer;
import com.msgc.repository.IAnswerRepository;
import com.msgc.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
/**
* Type: AnswerServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class AnswerServiceImpl implements IAnswerService{

    private IAnswerRepository answerRepository;
    
    @Autowired
    public void setAnswerRepository(IAnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    // 根据 id 查找，只返回一个，一般用于文件下载，查询文件所在路径
    @Override
    public Answer findById(Integer id) {
        return answerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Answer> findAll(Answer example) {
        return answerRepository.findAll(Example.of(example));
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

    /**
     * 根据 表字段id ，查出所有填写数据
     * @param fieldIdList 表字段id
     * @return 填写数据列表列表
     */
    @Override
    public List<Answer> findAllByFieldIds(List<Integer> fieldIdList){
        return answerRepository.findByFieldIdIn(fieldIdList);
    }

}

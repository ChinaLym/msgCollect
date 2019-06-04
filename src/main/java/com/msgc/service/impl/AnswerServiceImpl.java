package com.msgc.service.impl;

import com.msgc.constant.FilePath;
import com.msgc.entity.Answer;
import com.msgc.repository.IAnswerRepository;
import com.msgc.service.IAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@Slf4j
@CacheConfig(cacheNames = "answerCache")
public class AnswerServiceImpl implements IAnswerService{

    private IAnswerRepository answerRepository;
    
    @Autowired
    public AnswerServiceImpl(IAnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    // 根据 id 查找，只返回一个，一般用于文件下载，查询文件所在路径
    @Override
    @Cacheable(key = "'id' + #id")
    public Answer findById(Integer id) {
        return answerRepository.findById(id).orElse(null);
    }

    /**
     * 通过填写记录，找出这个填写记录下所有填写的内容
     * @param recordId 填写记录 id
     * @return 填写内容 List
     */
    @Cacheable(key = "'r' + #recordId")
    @Override
    public List<Answer> findAllByRecordId(Integer recordId) {
        return answerRepository.findAllByAnswerRecordId(recordId);
    }

    @CacheEvict(allEntries = true)
    @Override
    public List<Answer> save(List<Answer> answersList) {
        return answerRepository.saveAll(answersList);
    }

    //删除时如果 type 是文件 也要把原来文件删掉
    @CacheEvict(key="'r' + #answerRecordId")
    @Override
    public void deleteAllByRecordId(Integer answerRecordId) {
        Answer answerExample = new Answer();
        answerExample.setAnswerRecordId(answerRecordId);
        List<Answer> answerList = answerRepository.findAll(Example.of(answerExample));
        answerList.stream()
                .filter(answer -> "文件".equals(answer.getType()))
                .forEach(answer -> {
                    //删除之前的文件
                    File oldFile = new File(FilePath.getRealPath(answer.getContent()));
                    if(!oldFile.delete()){
                        log.info("旧文件(" + oldFile.getAbsolutePath() +")删除失败，answerId=" + answer.getId());
                    }
                });
        answerRepository.deleteAllByRecordId(answerRecordId);
    }

    /**
     * 根据 表字段id ，查出所有填写数据
     * @param fieldIdList 表字段id
     * @return 填写数据列表列表
     */
    @Cacheable
    @Override
    public List<Answer> findAllByFieldIds(List<Integer> fieldIdList){
        return answerRepository.findByFieldIdIn(fieldIdList);
    }

}

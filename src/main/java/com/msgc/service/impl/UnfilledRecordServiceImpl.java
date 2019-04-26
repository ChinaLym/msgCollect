package com.msgc.service.impl;

import com.msgc.entity.UnfilledRecord;
import com.msgc.repository.IUnfilledRecordRepository;
import com.msgc.service.IUnfilledRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
/**
* Type: UnfilledTableServiceImpl
* Description: 业务逻辑实现类
* @author LYM
 */
@Service
public class UnfilledRecordServiceImpl implements IUnfilledRecordService {

    private final IUnfilledRecordRepository unfilledTableRepository;

    @Autowired
    public UnfilledRecordServiceImpl(IUnfilledRecordRepository unfilledTableRepository) {
        this.unfilledTableRepository = unfilledTableRepository;
    }

    @Override
    public UnfilledRecord save(UnfilledRecord unfilledTable) {
        return unfilledTableRepository.save(unfilledTable);
    }
    
    @Override
    public UnfilledRecord findById(Integer id) {
        Optional<UnfilledRecord> unfilledTable = unfilledTableRepository.findById(id);
        return unfilledTable.orElse(null);
    }
    
    @Override
    public List<UnfilledRecord> findAllById(List<Integer> idList) {
        return unfilledTableRepository.findAllById(idList);
    }
    
    @Override
    public List<UnfilledRecord> findAll(UnfilledRecord unfilledTableExample) {
        return unfilledTableRepository.findAll(Example.of(unfilledTableExample));
    }
    
    @Override
    public List<UnfilledRecord> findAll() {
        return unfilledTableRepository.findAll();
    }

    @Override
	public void deleteById(Integer id) {
		unfilledTableRepository.deleteById(id);
	}
    
    @Override
    public List<UnfilledRecord> save(List<UnfilledRecord> unfilledTableList) {
        return unfilledTableRepository.saveAll(unfilledTableList);
    }

    @Override
    public List<UnfilledRecord> findAllByUserId(Integer userId) {
        return unfilledTableRepository.findAllByUserIdAndFilled(userId, false);
    }

    @Override
    public void deleteByTableIds(List<Integer> tableIdList) {
        unfilledTableRepository.deleteAllByTableId(tableIdList);
    }

    @Override
    public UnfilledRecord findByUserIdAndTableId(Integer userId, Integer tableId) {
        return unfilledTableRepository.findByUserIdAndTableId(userId, tableId);
    }
}
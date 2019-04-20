package com.msgc.cache;

import com.msgc.entity.FieldType;
import com.msgc.service.IFieldTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:  FieldTypeFlyweightFactory
 * @Description:
 *      缓存所有字段类型
 * @Author:         LYM
 * @CreateDate:     2019/2/26 16:59
*/
@Component
public class FieldTypeFlyweightFactory {

	private static FieldTypeFlyweightFactory instance;

	private volatile Map<String, FieldType> cache = new HashMap<>();

	@Autowired
	private FieldTypeFlyweightFactory(IFieldTypeService fieldTypeService){
		this.fieldTypeService = fieldTypeService;
	}

	private final IFieldTypeService fieldTypeService;

	@PostConstruct
	public void initFieldTypeService() {
		instance = this;
	}


	public static FieldTypeFlyweightFactory getInstance(){
		return instance;
	}

	public FieldType getFlyweight(String key){
		if(cache.size() == 0){
			synchronized (fieldTypeService){
				if(cache.size() == 0){
					List<FieldType> allType = fieldTypeService.findAll();
					allType.forEach(
							fieldType -> cache.put(fieldType.getName(), fieldType)
					);
				}
			}
		}
		return cache.get(key);
	}

	public int getCacheSize(){
		return cache.size();
	}
}

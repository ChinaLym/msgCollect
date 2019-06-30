package com.msgc.demo.excel;

import com.msgc.config.WebMvcConfig;
import com.msgc.entity.Answer;
import com.msgc.entity.bo.AnswerRecordBO;
import com.msgc.entity.Field;
import com.msgc.utils.excel.ExcelUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelUtilAdapter {

    private static final String DEFAULT_EXCEL_DIR = WebMvcConfig.FILE_DIR;

    public static String write(String tableName,
                               List<Field> fieldList,
                               List<AnswerRecordBO> answerRecordBOList) throws IOException {

        String fileName = DEFAULT_EXCEL_DIR + UUID.randomUUID().toString();
        List<List<String>> headers = new ArrayList<>();
        fieldList.sort(Comparator.comparing(Field::getNum));
        //处理表头
        for (Field field : fieldList){
            List<String> column = new ArrayList<>();
            column.add(field.getName());
            headers.add(column);
        }
        headers.add(Collections.singletonList("操作 ip"));
        headers.add(Collections.singletonList("使用系统"));
        headers.add(Collections.singletonList("使用浏览器"));
        headers.add(Collections.singletonList("操作时间"));
        //数据
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<List<String>> data = new ArrayList<>(answerRecordBOList.size());
        for (AnswerRecordBO answerRecordBO : answerRecordBOList){
            List<String> row = answerRecordBO.getAnswerList().stream()
                    .filter( answer -> !"文件".equals(answer.getType()))
                    .map(Answer::getContent).collect(Collectors.toList());
            row.add(answerRecordBO.getIp());
            row.add(answerRecordBO.getDeviceSystem());
            row.add(answerRecordBO.getBrowser());
            row.add(sdFormatter.format(answerRecordBO.getUpdateTime()));
            data.add(row);
        }
        ExcelUtil.write(fileName, tableName, headers, data);
        return fileName;
    }
}

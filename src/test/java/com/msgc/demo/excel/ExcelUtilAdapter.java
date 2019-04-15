package com.msgc.demo.excel;

import com.msgc.entity.bo.AnswerRecordBO;
import com.msgc.entity.Field;
import com.msgc.utils.excel.ExcelUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelUtilAdapter {

    public static final String DEFAULT_EXCEL_DIR = "F:\\msg_collect_files\\";

    public static String write(String tableName,
                               List<Field> fieldList,
                               List<AnswerRecordBO> answerRecordBOList) throws IOException {

        String fileName = DEFAULT_EXCEL_DIR + UUID.randomUUID().toString();
        List<List<String>> headers = new ArrayList();
        fieldList.sort(Comparator.comparing(Field::getNum));
        //处理表头
        for (Field field : fieldList){
            List<String> coulumn = new ArrayList();
            coulumn.add(field.getName());
            headers.add(coulumn);
        }
        headers.add(Arrays.asList("操作 ip"));
        headers.add(Arrays.asList("使用系统"));
        headers.add(Arrays.asList("使用浏览器"));
        headers.add(Arrays.asList("操作时间"));
        //数据
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<List<String>> data = new ArrayList();
        for (AnswerRecordBO answerRecordBO : answerRecordBOList){
            List<String> row = answerRecordBO.getAnswerList().stream()
                    .filter( answer -> !"文件".equals(answer.getType()))
                    .map( answer -> answer.getContent()).collect(Collectors.toList());
            row.add(answerRecordBO.getIp());
            row.add(answerRecordBO.getDeviceSystem());
            row.add(answerRecordBO.getBrowser());
            row.add(sdFormatter.format(answerRecordBO.getUpdate_time()));
            data.add(row);
        }
        ExcelUtil.write(fileName, tableName, headers, data);
        return fileName;
    }
}

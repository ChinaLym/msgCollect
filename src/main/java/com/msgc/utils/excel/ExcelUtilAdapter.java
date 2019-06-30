package com.msgc.utils.excel;

import com.msgc.constant.FilePath;
import com.msgc.constant.enums.ExcelReadStrategyEnum;
import com.msgc.entity.bo.AnswerRecordBO;
import com.msgc.entity.bo.ExcelReadStrategy;
import com.msgc.entity.Field;
import com.msgc.entity.Table;
import com.msgc.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelUtilAdapter {

    /**
     *  将收集表信息导出至excel
     * @param table                 根据table.name导出的excel名称
     * @param fieldList             表的列
     * @param answerRecordBOList    收集到的数据
     * @return  写好的文件的路径
     * @throws IOException
     */
    public static File write(Table table,
                               List<Field> fieldList,
                               List<AnswerRecordBO> answerRecordBOList) throws IOException {

        String fileName = FilePath.getTableProcessedPath(table.getId()) + FilePath.FILE_NAME_ALL_FIELDS_EXCEL;
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
        //为了方便根据用 fieldId 获取 field
        Map<Integer, Field> tempMap = new HashMap<>(fieldList.size());
        fieldList.forEach(filed -> tempMap.put(filed.getId(), filed));
        //数据
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<List<String>> data = new ArrayList<>();
        for (AnswerRecordBO answerRecordBO : answerRecordBOList){
            List<String> row = answerRecordBO.getAnswerList().stream()
                    //.filter( answer -> !"文件".equals(answer.getType()))
                    // "" 换成生成下载该文件的 URL********************
                    .map( answer -> !"文件".equals(answer.getType()) ? answer.getContent() :
                            ExcelUtil.generateHyperlink(
                                    FilePath.generateDownloadURL(table.getId(), tempMap.get(answer.getFieldId()), FileUtil.getFileName(answer.getContent())),
                                    FileUtil.getFileName(answer.getContent()))
                    )
                    .collect(Collectors.toList());
            row.add(answerRecordBO.getIp());
            row.add(answerRecordBO.getDeviceSystem());
            row.add(answerRecordBO.getBrowser());
            row.add(sdFormatter.format(answerRecordBO.getUpdateTime()));
            data.add(row);
        }
        return ExcelUtil.write(fileName, table.getName(), headers, data);
    }

    /**
     *
     * @param fileName  文件名，含扩展名，不包含路径
     * @param table     表，返回值
     * @param fieldList 字段，返回值
     * @param strategy  读取策略
     * @throws IOException 读取出错
     */
    public static void read(String fileName, Table table, List<Field> fieldList, ExcelReadStrategy strategy) throws IOException {
        //fieldList = new ArrayList<>();
        //table = new Table();
        File file =new File(FilePath.BASE_DIR + FilePath.DEFAULT_EXCEL_UPLOAD_DIR + fileName);
        if (!file.exists()){
            System.out.println(fileName + "not exists");
            return;
        }

        List<Object> objectList = ExcelUtil.read(file, strategy.getSheetIndex());
        if(objectList.size() > 0){
            //定位到 Field_position 所在行，内容类似 [xx,xxx,xxxx]，defaultValue默认为下一行
            String fields = objectList.get(strategy.getField_position()).toString();
            String defaultValues = objectList.get(strategy.getField_position() + 1).toString();
            //去掉两侧中括号
            fields = fields.substring(1, fields.length() - 1);
            defaultValues = defaultValues.substring(1, defaultValues.length() - 1);
            //将改行切分成单元格
            String[] fieldNameCells = fields.split(",");
            String[] fieldDefaultValueCells = defaultValues.split(",");

            if(ExcelReadStrategyEnum.TABLE_NAME_BY_CONTENT == strategy.getTableNameStrategy()){
                //设置表名默认为第一行第一个单元格的内容
                String firstLine = objectList.get(0).toString();
                firstLine = firstLine.substring(1, firstLine.length() - 1);
                table.setName(firstLine.split(",")[0]);
            }
            //遍历每列（单元格），生成字段，或附带默认值
            for (int col = 0; col < fieldNameCells.length; col++) {
                if(col < strategy.getStart())
                    continue;
                if(col > strategy.getStart() + strategy.getFieldNum())
                    break;
                if(!StringUtils.isEmpty(fieldNameCells[col])){
                    Field field = new Field();
                    if (strategy.getWithDefaultValue() && col < fieldDefaultValueCells.length)
                        field.setDefaultValue(StringUtils.strip(fieldDefaultValueCells[col]));
                    field.setName(StringUtils.strip(fieldNameCells[col]));
                    fieldList.add(field);
                }
            }
        }
        
    }
}

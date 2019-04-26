package com.msgc.utils.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.*;
import java.util.List;

public class ExcelUtil {

    /*
    导出数据收集表
    单行表头
    数据在第一个sheet
     */
    public static File write(String excelName, String sheetName, List<List<String>> headers, List<List<String>> data) throws IOException {
        File exportExcel = new File(excelName);
        if(!exportExcel.exists())
            exportExcel.createNewFile();
        try (OutputStream out = new FileOutputStream(exportExcel)) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName(sheetName);
            Table table = new Table(1);
            table.setHead(headers);
            writer.write0(data, sheet1, table);
            writer.finish();
        }
        return exportExcel;
    }

    //从excel中读取
    public static List<Object> read(File file, int sheetNo) throws IOException{
        try(BufferedInputStream is = new BufferedInputStream(new FileInputStream(file))) {
            return EasyExcelFactory.read(is, new Sheet(sheetNo, 0));
        }
    }

    /**
     * 为表格中生成 快速访问网络中资源 的函数
     * @param url           要访问的url
     * @param resourceName  在 excel 中显示的名称
     * @return 函数体，示例：'=HYPERLINK("http://www.msg_c.com/api/download/v1/field?field_id=1&user_id=1","xxx材料")'
     */
    public static String generateHyperlink(String url, String resourceName){
        StringBuilder hyperlink = new StringBuilder("=HYPERLINK(\"");
        hyperlink.append(url);
        hyperlink.append("\",\"");
        hyperlink.append(resourceName);
        hyperlink.append("\")");
        return hyperlink.toString();
    }

}

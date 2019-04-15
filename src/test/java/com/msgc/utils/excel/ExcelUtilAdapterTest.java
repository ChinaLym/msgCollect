package com.msgc.utils.excel;

import com.msgc.constant.enums.ExcelReadStrategyEnum;
import com.msgc.entity.bo.ExcelReadStrategy;
import com.msgc.entity.Field;
import com.msgc.entity.Table;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtilAdapterTest {

    @Test
    //从excel中读取
    public void testRead1() {
        Table table = new Table();
        List<Field> fieldList = new ArrayList<>();
        ExcelReadStrategy strategy = new ExcelReadStrategy();
        strategy.setField_position(0);
        strategy.setStart(0);
        strategy.setFieldNum(10);
        strategy.setTableNameStrategy(ExcelReadStrategyEnum.TABLE_NAME_BY_FILENAME);
        strategy.setWithDefalutValue(true);
        try {
            ExcelUtilAdapter.read("test.xls", table, fieldList, strategy);
            System.out.println("table---" + table);
            System.out.println("----------------");
            for (Field field :fieldList){
                System.out.println(field);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

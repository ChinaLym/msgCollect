package com.msgc.utils.excel;

import org.junit.Test;

import java.io.File;

public class ExcelUtilTest {

    @Test
    //从excel中读取
    public void testRead1() {
        try{
            for (Object o : ExcelUtil.read(new File("C:\\Users\\Administrator\\Desktop\\xxx.xls"), 1)){
                System.out.println(o);
                String[] strings = o.toString().split(", ");
                for (String s : strings){
                    System.out.println(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.msgc.utils.zip;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ZipUtilTest {

    @Test
    public void testZip1() {
        // 测试压缩方法1，压缩多个文件
        try {
            List<File> fileList = new ArrayList<>();
            fileList.add(new File("G:\\soft_ware\\Utils\\Adb\\adb.exe"));
            fileList.add(new File("G:\\files\\SSH\\id_rsa_1024"));
            FileOutputStream fos2 = new FileOutputStream(new File("c:/test1.zip"));
            ZipUtil.zip(fileList, fos2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZip2() {
        //测试压缩方法2, 压缩一个目录或文件
        try {
            FileOutputStream fos1 = new FileOutputStream(new File("c:/test2.zip"));
            ZipUtil.zip("G:\\soft_ware\\Utils\\Adb", fos1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

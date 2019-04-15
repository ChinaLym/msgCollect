package com.msgc.utils;

import com.msgc.constant.FilePath;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

    /**
     * 将 content 写到file中
     * @param file
     * @param content
     * @throws Exception 
     */
    public static void writeFile(File file, byte[] content) throws Exception {
    	FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }
    
    /**
     * 获取文件名的后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        if(!fileName.contains(".")) {
            return "";
        }
        int dotIndex = fileName.indexOf(".");
        return fileName.substring(dotIndex);
    }

    public static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf(FilePath.FILE_SEPARATOR) + 1);
    }
    /*public static void main(String[] args) {
        System.out.println(FileUtil.getSuffix("abc"));
        System.out.println(FileUtil.getSuffix("abc.png"));
    }*/
}

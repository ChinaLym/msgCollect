package com.msgc.utils;

import com.msgc.constant.FilePath;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    /**
     * 将 content 写到file中
     * @param file 目标文件
     * @param content 需要写入的内容
     * @throws Exception 该方法需要上层捕获异常
     */
    public static void writeFile(File file, byte[] content) throws IOException {
    	try(FileOutputStream fos = new FileOutputStream(file)){
            fos.write(content);
        }

    }
    
    /**
     * 获取 fileName 文件名的后缀
     * @param fileName 参数
     * @return 后缀名
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

    // 保证目标文件存在
    public static void ensureExist(File file) {
        if(file == null)
            throw new NullPointerException("param file is Null!");
        // 已存在
        if(file.exists())
            return;
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            if(!fileParent.mkdirs()){
                throw new RuntimeException("parent path[" + fileParent.getAbsolutePath() + "] create fail!");
            }
        }
        try {
            if(!file.createNewFile()){
                throw new RuntimeException("file[" + file.getAbsolutePath() + "] create fail!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*public static void main(String[] args) {
        System.out.println(FileUtil.getSuffix("abc"));
        System.out.println(FileUtil.getSuffix("abc.png"));
    }*/
}

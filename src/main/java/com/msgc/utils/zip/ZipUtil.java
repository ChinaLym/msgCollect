package com.msgc.utils.zip;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @ClassName:  ZipUtil
 * @Description:
 *      zip 工具类，压缩目录或文件
 *      第三方依赖：ant （解决中文乱码）
 * @Author:         LYM
 * @CreateDate:     2019/3/22 0:17
*/
public class ZipUtil {
    private static final int  BUFFER_SIZE = 2 * 1024;

    private static final String FILE_SEPARATOR = java.io.File.separator;

    /**
     * 将多个文件压缩成ZIP 方法，且不会保留文件结构
     * （因为多个文件可能路径不同，若想保留路径，第一参数更改为 目录路径）
     * @param srcFiles 需要压缩的文件列表，不能为文件夹，否则会报无权限错误
     * @param out           压缩文件输出流
     * @throws IOException 压缩失败会抛出运行时异常
     */
    public static void zip(List<File> srcFiles , OutputStream out)throws IOException {
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(out)) {
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zipOutputStream.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zipOutputStream.write(buf, 0, len);
                }
                zipOutputStream.closeEntry();
                in.close();
            }
        }
    }


    /**
     * 将单个文件或目录压缩成ZIP
     * @param srcDir 压缩文件夹路径
     * @param out    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构 true:保留
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws IOException 压缩失败会抛出运行时异常
     */
    public static void zip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws IOException{
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(out)){
            File sourceFile = new File(srcDir);
            compress(sourceFile,zipOutputStream,sourceFile.getName(), KeepDirStructure);
        }
    }
    //默认保留目录结构
    public static void zip(String srcDir, OutputStream out) throws IOException{
        zip(srcDir, out, true);
    }
    /**
     * 递归压缩方法
     * @param sourceFile 待压缩的源文件或目录
     * @param zipOutputStream        zip输出流
     * @param zipName       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构
     *                          true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下 (注：可能会出现同名文件导致压缩失败)
     * @throws IOException 写zip时可能出现异常
     */
    private static void compress(File sourceFile, ZipOutputStream zipOutputStream, String zipName,
                                 boolean KeepDirStructure) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE];
        //sourceFile为 单一文件（递归出口）
        if(sourceFile.isFile()){
            // copy文件到zip输出流中
            int len;
            try(FileInputStream in = new FileInputStream(sourceFile)) {
                zipOutputStream.putNextEntry(new ZipEntry(zipName));
                while ((len = in.read(buf)) != -1) {
                    zipOutputStream.write(buf, 0, len);
                }
                // Complete the entry
                zipOutputStream.closeEntry();
            }
        }
        //sourceFile 为目录
        else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zipOutputStream.putNextEntry(new ZipEntry(zipName + FILE_SEPARATOR));
                    // 没有文件，不需要文件的copy
                    zipOutputStream.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构，进入递归调用
                    if (KeepDirStructure) {
                        // file.getName()前面需要带上父文件夹的名字加一斜杠(FILE_SEPARATOR) 从而保留目录结构
                        compress(file, zipOutputStream, zipName + FILE_SEPARATOR + file.getName(), true);
                    } else {
                        compress(file, zipOutputStream, file.getName(), false);
                    }

                }
            }
        }
    }

}

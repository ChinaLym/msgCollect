package com.msgc.utils;

import com.msgc.constant.FilePath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

public class FileTransportUtil {

    /**
     * 下载文件, 禁止浏览器直接打开
     *
     * @param file          要传输给用户的文件的
     * @param transportName 传输给用户的文件名，若为 空 或null，则为文件名，若非空，需要添加格式
     * @throws IOException  向浏览器传输时，如网络中断等产生异常
     */
    public static void downloadFile(File file, String transportName) throws IOException {
        transportFile(file, transportName, "application/octet-stream");
    }

    /**
     * 让浏览器自己决定打开的方式，适合展示图片，
     * @param file
     * @param transportName
     * @throws IOException
     */
    public static void showImageJPEG(File file, String transportName) throws IOException {
        transportFile(file, transportName, "application/jpeg");
    }


    private static void transportFile(File file, String transportName, String contentType) throws IOException {
        // 如果文件名存在，则进行下载
        if (file.exists()) {
            // 配置文件下载
            HttpServletResponse response = WebUtil.getResponse();
            response.setHeader("content-type", contentType);
            response.setContentType(contentType);
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(
                            StringUtils.isEmpty(transportName) ? file.getName() : transportName, "UTF-8"));

            // 实现文件下载
            byte[] buffer = new byte[1024];
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                //success
            }
        }
    }
    //默认使用文件名
    public static void downloadFile(File file) throws IOException {
        downloadFile(file, null);
    }


    /**
     * 保存 web发送来的文件 到指定路径，返回值只有文件名不包含文件所在路径
     *
     * @param file web发送来的文件
     * @return 保存在本地的文件名，不包含路径
     * @throws IOException 网络异常出错
     */
    public static String uploadFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String oldFileName = file.getOriginalFilename();
            String randomStr = UUID.randomUUID().toString();
            String newFileName = randomStr + oldFileName.substring(oldFileName.lastIndexOf("."));
            File aimFile = new File(FilePath.BASE_DIR + FilePath.DEFAULT_EXCEL_UPLOAD_DIR, newFileName);
            file.transferTo(aimFile);
            return newFileName;
        }
        return null;
    }
}

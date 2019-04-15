package com.msgc.utils.qrCode;

import com.google.zxing.WriterException;
import com.msgc.constant.FilePath;
import com.msgc.controller.TableController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QrCodeUtilAdapter {
    //生成表分享的二维码，并缓存在本地
    public static void generate(Integer tableId) throws IOException, WriterException {
        //生成路径
        String outputPath = FilePath.getTableProcessedPath(tableId);
        File filePath = new File(outputPath);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        outputPath += FilePath.FILE_NAME_TABLE_QRCODE_JPEG;
        File jpegQrCode = new File(outputPath);
        if(!jpegQrCode.exists()){
            //不存则生成文件
            jpegQrCode.createNewFile();
            QrCodeUtil.createQrCode(new FileOutputStream(jpegQrCode), TableController.getShareCollectURL(tableId));
        }
    }
}

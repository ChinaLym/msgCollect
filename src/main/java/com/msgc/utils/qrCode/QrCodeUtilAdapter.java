package com.msgc.utils.qrCode;

import com.google.zxing.WriterException;
import com.msgc.constant.FilePath;
import com.msgc.controller.TableController;
import com.msgc.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QrCodeUtilAdapter {
    //生成表分享的二维码，并缓存在本地
    public static void generate(Integer tableId) throws IOException, WriterException {
        //生成路径
        String outputPath = FilePath.getTableProcessedPath(tableId) + FilePath.FILE_NAME_TABLE_QrCODE_JPEG;
        File jpegQrCode = new File(outputPath);
        FileUtil.ensureExist(jpegQrCode);
        QrCodeUtil.createQrCode(new FileOutputStream(jpegQrCode), TableController.getShareCollectURL(tableId));

    }
}

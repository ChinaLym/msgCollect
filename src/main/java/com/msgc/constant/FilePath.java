package com.msgc.constant;

import com.msgc.config.WebMvcConfig;
import com.msgc.entity.Field;
import com.msgc.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一管理上传下载文件目录
 *
 * excel 上传的文件路径 BASE_DIR/upload/excel 用作暂时储存 后序可以将这里优化至内存，可提升至少100倍的IO效率
 * 收集表内上传的文件路径形式为 BASE_DIR/upload/tableId_tableName/fieldId_fieldName/userId_userName_FieldName.xxx
 * 收集表数据导出，导出过的zip 文件将缓存在
 *      BASE_DIR/processed/tableId_tableName/all.zip
 *      BASE_DIR/processed/tableId_tableName/fieldId_fieldName.zip
 *      导出时，若已有缓存且数据无变化，则直接从缓存进行IO，否则在以上目录生成
 */
public class FilePath {

    public static final String FILE_SEPARATOR = java.io.File.separator;

    public static final String BASE_DIR = WebMvcConfig.FILE_DIR;

    public static final String DEFAULT_EXCEL_UPLOAD_DIR = "upload" + FILE_SEPARATOR;
    private static final String DEFAULT_PROCESSED_PATH = "processed" + FILE_SEPARATOR;

    private static final String FILE_NAME_CONNECT = "_";

    public static final String FILE_NAME_ALL_FIELDS_ZIP = "all.zip";
    public static final String FILE_NAME_ALL_FIELDS_EXCEL = "all.xlsx";
    private static final String FILE_NAME_QR_CODE_SUFFIX= ".jpeg";


    /**
     * 获取文件的相对路径 （相对于 BASE_DIR）
     * @param realPath 实际路径
     * @return 相对路径
     */
    public static String getRelativePath(String realPath){
        return realPath.replace(BASE_DIR, "").replace("\\", "/");
    }

    /**
     * 获取文件的实际路径 （相对于 BASE_DIR）
     * @param relativePath 相对路径
     * @return 实际路径
     */
    public static String getRealPath(String relativePath){
        return BASE_DIR + relativePath;
    }

    /**
     * 获取这个 表 的根目录
     * @param tableId 收集表id
     * @return 路径 String, 示例：   BASE_DIR/upload/{tableId}/
     */
    public static String getTableFilesUploadPath(Integer tableId){
        StringBuilder resourceDirPath = new StringBuilder(BASE_DIR + DEFAULT_EXCEL_UPLOAD_DIR);
        resourceDirPath.append(tableId);
        resourceDirPath.append(FILE_SEPARATOR);
        //
        return resourceDirPath.toString();
    }

    /**
     * 获取这个 表 的 all.zip  all.excel  num_fieldName.zip 路径
     * @param tableId 收集表
     * @return 路径 String, 示例：  BASE_DIR/processed/{tableId}/
     */
    public static String getTableProcessedPath(Integer tableId){
        StringBuilder zipPath = new StringBuilder(BASE_DIR + DEFAULT_PROCESSED_PATH);
        zipPath.append(tableId);
        zipPath.append(FILE_SEPARATOR);
        return zipPath.toString();
    }

    /**
     * 获取这个 表 对应域名的二维码缓存文件路径
     * @param tableId 收集表id
     * @return 二维码文件路径 String, 示例：  BASE_DIR/processed/{tableId}/xxx.com.jpeg
     */
    public static String getQrCodeFilePath(Integer tableId){
        return BASE_DIR + getQrCodePath(tableId);
    }

    /**
     * 获取这个 表 对应域名的二维码 URL
     * @param tableId 收集表id
     * @return 二维码文件路径 String, 示例：  BASE_DIR/processed/{tableId}/xxx.com.jpeg
     */
    public static String getQrCodeUrl(Integer tableId){
        return WebMvcConfig.VIRTUAL_DIR + getQrCodePath(tableId);
    }

    private static String getQrCodePath(Integer tableId){
        HttpServletRequest request = WebUtil.getRequest();
        StringBuilder zipPath = new StringBuilder(DEFAULT_PROCESSED_PATH);
        zipPath.append(tableId);
        zipPath.append(FILE_SEPARATOR);
        zipPath.append(request.getServerName());
        zipPath.append(FILE_NAME_CONNECT);
        zipPath.append(request.getServerPort());
        zipPath.append(FILE_NAME_QR_CODE_SUFFIX);
        return zipPath.toString();
    }

    /**
     * 获取这个 表字段 的根目录
     * @param tableId 收集表id
     * @param field 表字段
     * @return 路径 String, 示例： BASE_DIR/upload/{tableId}/{fieldId}_{fieldName}/
     */
    public static String getFieldFilesUploadPath(Integer tableId, Field field){
        StringBuilder resourceDirPath = new StringBuilder(getTableFilesUploadPath(tableId));
        resourceDirPath.append(field.getNum());
        resourceDirPath.append(FILE_NAME_CONNECT);
        resourceDirPath.append(field.getName());
        resourceDirPath.append(FILE_SEPARATOR);
        return resourceDirPath.toString();
    }

    /**
     * 获取这个 表字段 压缩后文件的命名
     * @param field 表字段(num_name)
     * @return zip名称：fieldNum_fieldName.zip
     */
    public static String getFieldZipName(Field field){
        StringBuilder fieldZipPath = new StringBuilder(field.getNum());
        fieldZipPath.append(FILE_NAME_CONNECT);
        fieldZipPath.append(field.getName());
        fieldZipPath.append("zip");
        return fieldZipPath.toString();
    }


    /**
     * 生成下载某个文件的 URL
     * @return 匹配 BaseController downloadResource 的 URL
     *          如：/api/v1/download/table/{tableId}/field/{fieldIdAndName}/{fileName}
     */
    public static String generateDownloadURL(int tableId, Field field, String fileName){
        if(field == null)
            return null;
        return WebUtil.getServerURL() +  "/api/v1/download/table/" +
        tableId + "/field/" + field.getNum() + FILE_NAME_CONNECT + field.getName() + "/" + fileName;
    }
}

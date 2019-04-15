package com.msgc.constant;

import com.msgc.entity.Field;
import com.msgc.utils.SpringUtil;

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
    private static final String PROJECT_FILES_DIR_ROOT = "F:";
    private static final String PROJECT_FILES_NAME = "msg_collect_files";

    private static final String BASE_DIR = PROJECT_FILES_DIR_ROOT + FILE_SEPARATOR + PROJECT_FILES_NAME + FILE_SEPARATOR;

    public static final String DEFAULT_EXCEL_UPLOAD_DIR = BASE_DIR + "upload" + FILE_SEPARATOR;
    public static final String DEFAULT_PROCESSED_DIR = BASE_DIR + "processed" + FILE_SEPARATOR;

    private static final String FILE_NAME_CONNECT = "_";

    public static final String FILE_NAME_ALL_FIELDS_ZIP = "all.zip";
    public static final String FILE_NAME_ALL_FIELDS_EXCEL = "all.xlsx";
    public static final String FILE_NAME_TABLE_QRCODE_JPEG= "QrCode.jpeg";


    /**
     * 获取这个 表 的根目录
     * @param tableId 收集表id
     * @return 路径 String, 示例：   BASE_DIR/upload/{tableId}/
     */
    public static String getTableFilesUploadPath(Integer tableId){
        StringBuilder resourceDirPath = new StringBuilder(FilePath.DEFAULT_EXCEL_UPLOAD_DIR);
        resourceDirPath.append(tableId);
        resourceDirPath.append(FilePath.FILE_SEPARATOR);
        //
        return resourceDirPath.toString();
    }

    /**
     * 获取这个 表 的 all.zip  all.excel  num_fieldName.zip 路径
     * @param tableId 收集表
     * @return 路径 String, 示例：  BASE_DIR/processed/{tableId}/
     */
    public static String getTableProcessedPath(Integer tableId){
        StringBuilder zipPath = new StringBuilder(FilePath.DEFAULT_PROCESSED_DIR);
        zipPath.append(tableId);
        zipPath.append(FilePath.FILE_SEPARATOR);
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
        resourceDirPath.append(FilePath.FILE_NAME_CONNECT);
        resourceDirPath.append(field.getName());
        resourceDirPath.append(FilePath.FILE_SEPARATOR);
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
    public static String generaterDownloadURL(int tableId, Field field, String fileName){
        if(field == null)
            return null;
        return SpringUtil.getServerURL() +  "/api/v1/download/table/" +
        tableId + "/field/" + field.getNum() + FILE_NAME_CONNECT + field.getName() + "/" + fileName;
    }
}

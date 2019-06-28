package com.msgc.constant;

import com.msgc.config.WebMvcConfig;
import com.msgc.entity.Field;
import com.msgc.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 统一管理上传下载文件目录
 *
 * excel 上传的文件路径 BASE_DIR/upload/excel 用作暂时储存 后序可以将这里优化至内存，可提升至少100倍的IO效率
 * 收集表内上传的文件路径形式为 BASE_DIR/upload/tableId_tableName/fieldId_fieldName/userId_userName_FieldName.xxx
 * 收集表数据导出，导出过的zip 文件将缓存在
 *      BASE_DIR/processed/tableId_tableName/all.zip
 *      BASE_DIR/processed/tableId_tableName/fieldId_fieldName.zip
 *      导出时，若已有缓存且数据无变化，则直接从缓存进行IO，否则在以上目录生成
 *
 *      DIR 表示可以访问的实际路径
 *      PATH 表示相对路径
 *      URL 表示可以访问的 URL
 *
 */
public class FilePath {

    // 文件系统路径分隔符，以兼容不同系统
    public static final String FILE_SEPARATOR = java.io.File.separator;
    // 根目录
    public static final String BASE_DIR = WebMvcConfig.FILE_DIR;

    // 上传文件根路径
    public static final String DEFAULT_EXCEL_UPLOAD_DIR = "upload" + FILE_SEPARATOR;
    // 处理过的缓存文件根路径
    private static final String DEFAULT_PROCESSED_PATH = "processed" + FILE_SEPARATOR;

    // 基础头像文件夹路径
    private static final String BASE_HEAD_IMAGE_PATH = "headImage/base/";
    // 基础头像文件夹完整路径
    public static final String BASE_HEAD_IMAGE_FULL_DIR = BASE_DIR + BASE_HEAD_IMAGE_PATH;
    // 基础头像URL前缀
    public static final String BASE_HEAD_IMAGE_URL_PERFFIX = WebMvcConfig.VIRTUAL_DIR + BASE_HEAD_IMAGE_PATH;

    // 上传的头像路径
    public static final String UPLOAD_HEAD_IMAGES_PATH = "headImage" + FILE_SEPARATOR + "upload" + FILE_SEPARATOR;
    // 上传头像文件夹完整路径
    public static final String UPLOAD_HEAD_IMAGE_FULL_DIR = BASE_DIR + UPLOAD_HEAD_IMAGES_PATH;
    // 上传头像URL前缀
    public static final String UPLOAD_HEAD_IMAGE_URL_PREFFIX = WebMvcConfig.VIRTUAL_DIR + UPLOAD_HEAD_IMAGES_PATH;


    // 文件名连接符
    private static final String FILE_NAME_CONNECT = "_";

    // 固定名称
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
        return BASE_DIR + DEFAULT_EXCEL_UPLOAD_DIR + tableId +
                FILE_SEPARATOR;
    }

    /**
     * 获取这个 表 的 all.zip  all.excel  num_fieldName.zip 路径
     * @param tableId 收集表
     * @return 路径 String, 示例：  BASE_DIR/processed/{tableId}/
     */
    public static String getTableProcessedPath(Integer tableId){
        return BASE_DIR + DEFAULT_PROCESSED_PATH + tableId +
                FILE_SEPARATOR;
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
        return DEFAULT_PROCESSED_PATH + tableId +
                FILE_SEPARATOR +
                request.getServerName() +
                FILE_NAME_CONNECT +
                request.getServerPort() +
                FILE_NAME_QR_CODE_SUFFIX;
    }

    /**
     * 获取这个 表字段 的根目录
     * @param tableId 收集表id
     * @param field 表字段
     * @return 路径 String, 示例： BASE_DIR/upload/{tableId}/{fieldId}_{fieldName}/
     */
    public static String getFieldFilesUploadPath(Integer tableId, Field field){
        return getTableFilesUploadPath(tableId) + field.getNum() +
                FILE_NAME_CONNECT +
                field.getName() +
                FILE_SEPARATOR;
    }

    /**
     * 获取这个 表字段 压缩后文件的命名
     * @param field 表字段(num_name)
     * @return zip名称：fieldNum_fieldName.zip
     */
    public static String getFieldZipName(Field field){
        return field.getNum() + FILE_NAME_CONNECT +
                field.getName() +
                "zip";
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

    /**
     * 获取新的头像文件的路径
     * @return 头像路径 + UUID.jpg
     */
    public static String getNewHeadImagesPath(){
        return BASE_DIR + UPLOAD_HEAD_IMAGES_PATH + UUID.randomUUID().toString() + ".jpg";
    }

    /**
     * 获取用户上传的头像的 URL
     * @param fileName 头像文件名称 xxx.jpg
     * @return 浏览器访问该头像图片的 URL，示例：  VIRTUAL_DIR/headImage/upload/{fileName}
     */
    public static String getHeadImageUrl(String fileName){
        return (WebMvcConfig.VIRTUAL_DIR + UPLOAD_HEAD_IMAGES_PATH + fileName).replace("\\", "/");
    }

}

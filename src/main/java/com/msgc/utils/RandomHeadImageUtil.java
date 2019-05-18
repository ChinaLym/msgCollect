package com.msgc.utils;

import com.msgc.config.WebMvcConfig;

import java.io.File;
import java.util.Random;

public class RandomHeadImageUtil {
    // 默认头像路径
    private static final String HEAD_IMAGE_BASE_DIR = "headImage/base/";
    // 完整路径
    private static final String FULL_DIR = WebMvcConfig.FILE_DIR + HEAD_IMAGE_BASE_DIR;
    // 初始化目标文件夹下有多少张图片，作为随机函数的上限
    private static int MAX_INDEX;
    // 生成规则
    private static final String FILE_URL_TEMPLATE = WebMvcConfig.VIRTUAL_DIR + HEAD_IMAGE_BASE_DIR + "img_%s.jpg";

    static{
        File file= new File(FULL_DIR);
        String[] files;
        if(file.exists() && (files = file.list()) != null){
            MAX_INDEX = files.length;
        }
    }
    public static Random random = new Random();

    public static String next(){
        return String.format(FILE_URL_TEMPLATE, random.nextInt(MAX_INDEX));
    }
}

package com.msgc.utils;

import com.msgc.config.WebMvcConfig;

import java.io.File;
import java.util.Random;

public class RandomHeadImageUtil {
    private static String HEADIMAGE_BASE_DIR = "headImage/base/";
    private static String FULL_DIR = WebMvcConfig.FILE_DIR + HEADIMAGE_BASE_DIR;
    private static String FILE_URL_TEMPLATE = WebMvcConfig.VIRTUL_DIR + HEADIMAGE_BASE_DIR + "img_%s.jpg";
    private static int MAX_INDEX;
    //初始化目标文件夹下有多少张图片，作为随机函数的上限
    static{
        File file= new File(FULL_DIR);
        MAX_INDEX = file.list().length;
    }
    public static Random random = new Random();

    public static String next(){
        return String.format(FILE_URL_TEMPLATE, random.nextInt(MAX_INDEX));
    }
}

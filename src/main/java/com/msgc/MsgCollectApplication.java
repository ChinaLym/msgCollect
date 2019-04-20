package com.msgc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@ServletComponentScan
public class MsgCollectApplication {
    //设置时区,出现时区乱码问题则使用 SET GLOBAL time_zone='+8:00';
    public static void main(String[] args) {
        SpringApplication.run(MsgCollectApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(3));    //单个文件最大 3M
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));//多个文件总大小最大为 10 M
        return factory.createMultipartConfig();
    }
}


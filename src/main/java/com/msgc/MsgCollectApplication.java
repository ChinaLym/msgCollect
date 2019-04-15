package com.msgc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

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
        factory.setMaxFileSize("5MB");
        factory.setMaxRequestSize("20MB");
        return factory.createMultipartConfig();
    }
}


package com.msgc.config;

import com.msgc.aop.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
* Type: WebMvcConfig
* Description: MVC相关配置
* 	所有与本项目相关的文件，请置于 FILE_DIR 下
* 		日志文件除外，日志单独保存
* @author LYM
* @date Dec 16, 2018
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    // 保存上传文件的目录, 如 E:/files/msg_collect_files/
    public static String FILE_DIR;;

    //虚拟路径, 即访问 url /VIRTUAL_DIR/xxx 会访问到  FILE_DIR/xxx
    public final static String VIRTUAL_DIR = "/collect_data/";

    //拦截虚拟路径所有请求
    private final static String VIRTUAL_DIR_Handle = VIRTUAL_DIR + "**";

    @Autowired
    public WebMvcConfig(LoginInterceptor loginInterceptor,  @Value("${fileDir}") String file_dir) {
        this.loginInterceptor = loginInterceptor;
        FILE_DIR = file_dir;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置LoginRequired，可以被SpringMVC识别，所有该被该拦截器拦截的都交给 spring MVC 处理
        /*registry.addInterceptor(new LoginRequiredInterceptor())
                .addPathPatterns("/**");*/
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login", "/register","/**/*.*");
    }

    /**
     * 配置静态资源的web访问路径，例如上传的文件 abc.png 保存在 E:/files/msg_collect_files 下
     * 那么在浏览器中访问的路径为：http://localhost:8080/collect_data/upload/abc.png
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(VIRTUAL_DIR_Handle).addResourceLocations(
                "file:///" + WebMvcConfig.FILE_DIR);
    }
    
}

package com.wangjp.sell;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SellApplication extends WebMvcConfigurationSupport {

    @Value("${upload.image.path}")
    public String imageRootPath;

    public static void main(String[] args) {
        SpringApplication.run(SellApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String[] rootDirList = imageRootPath.split("/");
        String rootDir = rootDirList[rootDirList.length - 1];
        registry.addResourceHandler("/" + rootDir +"/**").addResourceLocations("file:" + this.imageRootPath + "/");
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        super.addResourceHandlers(registry);
    }
}

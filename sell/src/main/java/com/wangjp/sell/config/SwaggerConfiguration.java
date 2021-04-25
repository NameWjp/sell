package com.wangjp.sell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/2/28 2:37 下午
 * @detail swagger 配置类
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerSetting() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger演示文档")
                .description("高仿饿了吗后台api")
                .version("0.0.1")
                .build();
    }
}

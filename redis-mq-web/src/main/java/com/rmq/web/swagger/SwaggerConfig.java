package com.rmq.web.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author mqz
 * @date 2020/4/16
 * @description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean(name = "default")
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).groupName("default")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rmq.web"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("在线接口文档")
                .description("蒙大拿")
                .license("copyRight@mqz")
                .description("接口描述文档")
                .termsOfServiceUrl("http://localhost:4000/doc.html")
                .contact("13128507506")
                .version("1.0")
                .build();
    }

}

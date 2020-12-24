package com.rlw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

/*  swagger分组
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("A");
    }*/

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //.enable(false)是否启动swagger
                .select()
                /**配置要扫描接口的方式
                 * basePackage:指定要扫描的包
                 * any():扫描全部
                 * none():不扫描*/
                .apis(RequestHandlerSelectors.basePackage("com.rlw.controller"))
                /**过滤路径*/
//                .paths(PathSelectors.ant("/rlw/swaggerdemo/**"))
                .build();
    }
    //配置swagger信息=apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("饶立玮","http://www.rlw.com","598449595@qq.com");
        return new ApiInfo("汽车租赁系统接口文档",
                "汽车租赁系统Api",
                "1.0",
                "http://localhost:8888/swagger-ui.html#/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }
}

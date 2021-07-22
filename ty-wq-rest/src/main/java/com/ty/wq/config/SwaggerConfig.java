package com.ty.wq.config;

import com.ty.wq.constant.Constants;
import com.ty.wq.utils.WsTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2020/10/9.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //@Bean
    public Docket createMonitorRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("接口文档")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ty.wq.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger RESTful APIs")
                .description("swagger RESTful APIs")
                //.termsOfServiceUrl("http://127.0.0.1:20000/")
                .version("1.0")
                .build();
    }
    @Bean
    public Docket restfulApi() {
        //添加head参数配置start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(Constants.WQ_TOKEN_HEADER).description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        //添加head参数配置end
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ty.wq.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(pars);
    }

}

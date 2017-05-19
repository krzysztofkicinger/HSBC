package com.kicinger.hsbc.configuration;

import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket getApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(getEndpointsDefinitionPath())
                .paths(getPathSelector())
                .build();
    }

    private Predicate<RequestHandler> getEndpointsDefinitionPath() {
        return RequestHandlerSelectors.withClassAnnotation(Api.class);
    }

    private Predicate<String> getPathSelector() {
        return PathSelectors.any();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "HSBC Code Challenge",
                "HSBC Code Challenge",
                "1.0",
                "Terms of service",
                new Contact("Krzysztof Kicinger", "", "krzysztofkicinger@gmail.com"),
                "Licence of the API",
                ""
        );
//        return ApiInfo.DEFAULT;
    }

}

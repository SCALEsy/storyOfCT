package test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//@Configuration
//@EnableSwagger2

public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean enable;

    @Bean
    public Docket swagger() {
        ApiInfo info = new ApiInfo("Test", "this id description",
                "1.0", "url",
                "contact", null, null);
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(info)
                .select()
                .apis(RequestHandlerSelectors.basePackage("test.rest"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false).enable(enable);
        return docket;
    }
}

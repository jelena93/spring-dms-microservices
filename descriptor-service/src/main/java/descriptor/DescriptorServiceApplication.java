package descriptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
//@EnableCircuitBreaker
public class DescriptorServiceApplication {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors
                                                                             .basePackage("descriptor.controller"))
                                                      .paths(PathSelectors.any()).build();
    }

    @LoadBalanced
    @Bean
    public OAuth2RestTemplate auth2RestTemplate(OAuth2ProtectedResourceDetails resourceDetails,
                                                OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(resourceDetails, clientContext);
    }

    public static void main(String[] args) {
        SpringApplication.run(DescriptorServiceApplication.class, args);
    }
}

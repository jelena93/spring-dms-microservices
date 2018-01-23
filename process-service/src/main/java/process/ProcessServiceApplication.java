package process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import process.messaging.input.DocumentInputChannel;
import process.messaging.output.DocumentOutputChannel;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableBinding({ DocumentInputChannel.class, DocumentOutputChannel.class })
public class ProcessServiceApplication {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.basePackage("process.controller"))
                                                      .paths(PathSelectors.any()).build();
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProcessServiceApplication.class, args);
    }

    //    @LoadBalanced
    //    @Bean
    //    public OAuth2RestTemplate auth2RestTemplate(OAuth2ProtectedResourceDetails resourceDetails,
    // OAuth2ClientContext clientContext) {
    //        return new OAuth2RestTemplate(resourceDetails, clientContext);
    //    }

}

package document;

import document.elasticsearch.DocumentIndexer;
import document.elasticsearch.ElasticClient;
import org.apache.tika.Tika;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class DocumentServiceApplication {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.basePackage("document.controller"))
                                                      .paths(PathSelectors.any()).build();
    }

    @Bean
    public Tika tika() {
        return new Tika();
    }

    @Bean
    public ElasticClient elasticClient() {
        return new ElasticClient();
    }

    @Bean
    public DocumentIndexer documentIndexer() {
        return new DocumentIndexer();
    }

    //    @Bean(name = "multipartResolver")
    //    public CommonsMultipartResolver multipartResolver() {
    //        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    //        resolver.setMaxUploadSize(3 * 1024 * 1024);
    //        return resolver;
    //    }
    //
    //    @Bean
    //    @Order(0)
    //    public MultipartFilter multipartFilter() {
    //        MultipartFilter multipartFilter = new MultipartFilter();
    //        multipartFilter.setMultipartResolverBeanName("multipartResolver");
    //        return multipartFilter;
    //    }
    public static void main(String[] args) {
        SpringApplication.run(DocumentServiceApplication.class, args);
    }
}

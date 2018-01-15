package auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebSecurity
public class AuthServiceApplication extends WebMvcConfigurerAdapter {
    
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}

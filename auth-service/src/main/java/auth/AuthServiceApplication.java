package auth;

import auth.messaging.UserInputChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
@Controller
@SessionAttributes("authorizationRequest")
@EnableBinding({UserInputChannel.class})
public class AuthServiceApplication extends WebMvcConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;

    @RequestMapping("/user")
    @ResponseBody
    public Authentication user(Authentication user) {
        return user;
    }

    @PostMapping(value = "/oauth/revoke-token")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        String authHeader = request.getHeader("Authorization");
        System.out.println("authHeader " + authHeader);
        if (authHeader != null) {
            String tokenValue = authHeader.split(" ")[1];
            System.out.println("token " + tokenValue);
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            System.out.println("access " + accessToken);
//            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
            tokenStore.removeAccessToken(accessToken);
            System.out.println("prisaoo");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            new SecurityContextLogoutHandler().logout(request, httpServletResponse, authentication);
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}

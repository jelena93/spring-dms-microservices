package gateway.controller;
//@RequestMapping("/logout")

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    String logoutUrl = "http://auth-service/uaa/oauth/revoke-token";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        Object details = authentication.getDetails();
        if (details.getClass().isAssignableFrom(OAuth2AuthenticationDetails.class)) {

            String accessToken = ((OAuth2AuthenticationDetails) details).getTokenValue();

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("access_token", accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "bearer " + accessToken);

            HttpEntity<String> request = new HttpEntity(params, headers);

            HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
            HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
            restTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter[]{formHttpMessageConverter, stringHttpMessageConverternew}));
            try {
                ResponseEntity<String> response = restTemplate.exchange(logoutUrl, HttpMethod.POST, request, String.class);
                System.out.println("### " + response);
            } catch (HttpClientErrorException e) {
                System.out.println("*** HttpClientErrorException invalidating token with SSO authorization server. response.status code: {}, server URL: {}" + logoutUrl);
            }
        }
        
        new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
        return "redirect:/";

    }

}

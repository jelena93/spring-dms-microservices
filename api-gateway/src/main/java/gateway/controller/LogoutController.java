package gateway.controller;

import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/loging")
public class LogoutController {

    @Autowired
    private OAuth2RestTemplate auth2RestTemplate;
    String logoutUrl = "http://auth-service/uaa/oauth/revoke-token";

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logouta(HttpServletRequest httpServletRequest,
            HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object details = auth.getDetails();

        if (details.getClass().isAssignableFrom(OAuth2AuthenticationDetails.class)) {

            String accessToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
            System.out.println("token: {}" + accessToken);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("access_token", accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "bearer " + accessToken);

            HttpEntity<String> request = new HttpEntity(params, headers);

            HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
            HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
            auth2RestTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter[]{formHttpMessageConverter, stringHttpMessageConverternew}));
            ResponseEntity<String> responses = auth2RestTemplate.exchange(logoutUrl, HttpMethod.POST, request, String.class);
            System.out.println("/// resposne " + responses);
        }

        HttpSession session = httpServletRequest.getSession(false);
        if (httpServletRequest.isRequestedSessionIdValid() && session != null) {
            session.invalidate();
        }
        handleLogOutResponse(httpServletRequest, response);
        return "redirect:/login";
    }

    /**
     * This method would edit the cookie information and make JSESSIONID empty
     * while responding to logout. This would further help in order to. This
     * would help to avoid same cookie ID each time a person logs in
     *
     * @param response
     */
    private void handleLogOutResponse(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("**** usao " + auth);
        Object details = auth.getDetails();
        if (details.getClass().isAssignableFrom(OAuth2AuthenticationDetails.class)) {

            String accessToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
            System.out.println("token: {}" + accessToken);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("access_token", accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "bearer " + accessToken);

            HttpEntity<String> request = new HttpEntity(params, headers);

            HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
            HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
            auth2RestTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter[]{formHttpMessageConverter, stringHttpMessageConverternew}));
            try {
                ResponseEntity<String> response = auth2RestTemplate.exchange(logoutUrl, HttpMethod.POST, request, String.class);
                System.out.println("/// resposne " + response);

                Cookie cookie0 = new Cookie("JSESSIONID", null);
                cookie0.setPath("/");
                cookie0.setValue("");
                cookie0.setHttpOnly(true);
                cookie0.setMaxAge(-1);
                httpServletResponse.addCookie(cookie0);
                ///////////////////////
                Cookie cookie2 = new Cookie("XSRF-TOKEN", null);
                cookie2.setPath("/");
                cookie2.setValue("");
                cookie2.setMaxAge(-1);
                httpServletResponse.addCookie(cookie2);
                httpServletRequest.getSession().invalidate();
                SecurityContextHolder.getContext().setAuthentication(null);

            } catch (HttpClientErrorException e) {
                System.out.println("HttpClientErrorException invalidating token with SSO authorization server. response.status code: {}, server URL: {}");
            }

        }

//        new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
        return "redirect:/login";
    }

}

//@Controller
////@RequestMapping("/logout")
//public class LogoutController {
//
//    private final OAuth2RestTemplate auth2RestTemplate;
//
//    @Autowired
//    public LogoutController(OAuth2RestTemplate auth2RestTemplate) {
//        this.auth2RestTemplate = auth2RestTemplate;
//    }
//    @GetMapping
//    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
//            Authentication authentication) {
//        Object details = authentication.getDetails();
//        if (details.getClass().isAssignableFrom(OAuth2AuthenticationDetails.class)) {
//
//            String accessToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
//
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.add("access_token", accessToken);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Authorization", "bearer " + accessToken);
//
//            HttpEntity<String> request = new HttpEntity(params, headers);
//
//            HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
//            HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
//            auth2RestTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter[]{formHttpMessageConverter, stringHttpMessageConverternew}));
//            try {
//                String response = auth2RestTemplate.postForObject("http://auth-service/uaa/oauth/revoke-token",
//                        request, String.class);
//                System.out.println("### " + response);
//                new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
//                SecurityContextHolder.getContext().setAuthentication(null);
//            } catch (HttpClientErrorException e) {
//                System.out.println("/// HttpClientErrorException invalidating token with SSO authorization "
//                        + "server. response.status code: {}, server URL: {} http://auth-service/uaa/oauth/revoke-token");
//            }
//        }
//
//        new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
//        return "redirect:/";
//
//    }
//}

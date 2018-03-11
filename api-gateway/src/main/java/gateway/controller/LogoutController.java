package gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {

//    String logoutUrl = "http://auth-service/uaa/oauth/revoke-token";
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @GetMapping
//    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
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
//            restTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter[]{formHttpMessageConverter, stringHttpMessageConverternew}));
//            try {
//                ResponseEntity<String> response = restTemplate.exchange(logoutUrl, HttpMethod.POST, request, String.class);
//                System.out.println("### " + response);
//            } catch (HttpClientErrorException e) {
//                System.out.println("*** HttpClientErrorException invalidating token with SSO authorization server. response.status code: {}, server URL: {}" + logoutUrl);
//            }
//        }
//        
//        new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
//        return "redirect:/";
//
//    }
}

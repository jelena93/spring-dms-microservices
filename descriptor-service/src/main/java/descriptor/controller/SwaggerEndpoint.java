package descriptor.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
@RefreshScope
public class SwaggerEndpoint {

	@GetMapping({ "", "/swagger", "/docs" })
	public String redirectSwager() {
		return "redirect:/swagger-ui.html";
	}

}

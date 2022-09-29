package br.com.ema.EmaServer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@OpenAPIDefinition(
		info = @Info(
				title = "APIs de App Ema",
				version = "1.0",
				description = "APIs de operação do aplicativo Ema",
				license = @License(name = "Apache 2.0", url = "https://foo.bar"),
				contact = @Contact(name = "Red Team Ame Cibersecurity", email = "redteam@amedigital.com")
		)
)
@SpringBootApplication
public class EmaServerApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(EmaServerApplication.class, args);
//		DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
//		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}
}

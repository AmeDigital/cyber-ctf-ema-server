package br.com.ema.EmaServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class EmaWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backup/**")
                .addResourceLocations("classpath:/backup/")
                .setCachePeriod(999999999);
        registry.addResourceHandler("/admin_utils/**")
                .addResourceLocations("classpath:/admin_utils/")
                .setCachePeriod(999999999);
    }
}
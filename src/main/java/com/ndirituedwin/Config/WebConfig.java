package com.ndirituedwin.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        WebMvcConfigurer.super.addCorsMappings(registry);
         registry.addMapping("/**")
//                 .allowedOrigins("http://localhost:4200")
                 .allowedOrigins("*")
//                 .allowedOriginPatterns("*")

                 .allowedMethods("*")
                 .maxAge(3600L)
                 .allowedHeaders("*")
                 .exposedHeaders("Authorization")
//                 .allowCredentials(true);
                 .allowCredentials(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }



}

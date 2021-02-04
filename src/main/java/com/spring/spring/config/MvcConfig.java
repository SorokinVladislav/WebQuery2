package com.spring.spring.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("login");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/hello").setViewName("login");
        registry.addViewController("/login").setViewName("login");
    }


}
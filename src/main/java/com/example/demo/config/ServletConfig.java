package com.example.demo.config;

import com.example.demo.servlet.SimpleStatusServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<SimpleStatusServlet> helloServlet() {
        ServletRegistrationBean<SimpleStatusServlet> bean = new ServletRegistrationBean<>(
            new SimpleStatusServlet(), "/simple-status");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
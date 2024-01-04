package com.server.tistoryproject.Config;

import io.opentelemetry.sdk.resources.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String resourcePath = "file:///TistoryImages/";
        String connectPath = "/image/{subfolder}/**";
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);
    }
}
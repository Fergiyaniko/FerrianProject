package com.example.servingwebcontent;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class MulterFile {
    /**
     * File upload configuration
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //Largest file
        factory.setMaxFileSize(DataSize.ofMegabytes(10L)); //KB,MB
        ///  Set the total size of the total uploaded data
        factory.setMaxRequestSize(DataSize.ofMegabytes(10L));
        return factory.createMultipartConfig();
    }
}

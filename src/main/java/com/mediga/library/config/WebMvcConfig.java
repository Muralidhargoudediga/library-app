package com.mediga.library.config;

import com.mediga.library.message.converter.JsonMergePatchHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private JsonMergePatchHttpMessageConverter jsonMergePatchHttpMessageConverter;

    @Autowired
    public void setJsonMergePatchHttpMessageConverter(JsonMergePatchHttpMessageConverter jsonMergePatchHttpMessageConverter) {
        this.jsonMergePatchHttpMessageConverter = jsonMergePatchHttpMessageConverter;
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jsonMergePatchHttpMessageConverter);
        addDefaultHttpMessageConverters(converters);
    }

}

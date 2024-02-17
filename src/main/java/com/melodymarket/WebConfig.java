package com.melodymarket;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                .filter(MappingJackson2HttpMessageConverter.class::isInstance)
                .forEach(converter -> {
                    MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
                    jacksonConverter.setDefaultCharset(StandardCharsets.UTF_8);
                    List<MediaType> mediaTypes = new ArrayList<>(jacksonConverter.getSupportedMediaTypes());
                    mediaTypes.add(0, new MediaType("application", "json", StandardCharsets.UTF_8));
                    jacksonConverter.setSupportedMediaTypes(mediaTypes);
                });
    }
}

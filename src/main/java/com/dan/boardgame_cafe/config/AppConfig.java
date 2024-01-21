package com.dan.boardgame_cafe.config;

import org.apache.http.HttpHeaders;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
    @Value("${api.openai.key}")
    private String openaiKey;
    @Value("${api.openai.url}")
    private String openaiURL;

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

    @Bean
    public WebClient chatGPTWebClient(){
        return WebClient.builder()
                .baseUrl(openaiURL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openaiKey)
                .build();
    }
}

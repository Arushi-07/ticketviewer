package com.zcc.ticketviewer.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MyRestTemplate  {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

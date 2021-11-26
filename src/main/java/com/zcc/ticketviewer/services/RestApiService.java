package com.zcc.ticketviewer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.exception.MyCustomException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

@Log4j2
@Component
public class RestApiService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;


    public String getResponse(final String requesturl, final String accountId, final String password) throws MyCustomException {
            String err = "";
            try{
                ResponseEntity<Object> response
                        = restTemplate.exchange(new URI(requesturl), HttpMethod.GET, new HttpEntity(createHeaders(accountId, password)),Object.class);
                return objectMapper.writeValueAsString(response.getBody());
            } catch(HttpClientErrorException | HttpServerErrorException ex ){
                throw new MyCustomException("Http Exceptions", ex.getStatusCode().value());

            } catch( ResourceAccessException ex){
                throw new MyCustomException(" Resource Success Exception", 500);
            } catch (URISyntaxException e) {
                throw new MyCustomException("Unable to generate URI", 500);
            } catch (IOException e) {
                throw new MyCustomException("Unable to parese response", 500);
            }
    }

    public HttpHeaders createHeaders(String accountId, String password){
        return new HttpHeaders() {{
            String encoding = null;
            try {
                encoding = Base64.getEncoder().encodeToString((accountId+":"+password).getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                encoding = "";
            }
            String authHeader = "Basic " + encoding;
            set( "Authorization", authHeader );
        }};
    }
}

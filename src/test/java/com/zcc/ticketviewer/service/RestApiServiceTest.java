package com.zcc.ticketviewer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.exception.MyCustomException;
import com.zcc.ticketviewer.services.RestApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestApiServiceTest {
    @InjectMocks
    public RestApiService restApiService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    ObjectMapper objectMapper;

    final String requestUrl = "";
    final String accountId = "hello";
    final String password = "world";

    @Test
    public void getResponseSuccess() throws MyCustomException, URISyntaxException, JsonProcessingException {
        final String response = "Hello World";
        when(restTemplate.exchange(new URI(requestUrl), HttpMethod.GET, new HttpEntity(restApiService.createHeaders(accountId, password)), Object.class)).thenReturn(new ResponseEntity<Object>("Hello World",HttpStatus.OK));
        when(objectMapper.writeValueAsString(any())).thenReturn(response);
        String resp = restApiService.getResponse(requestUrl, accountId, password);
        assertEquals(resp, response);
    }

    @Test(expected = MyCustomException.class)
    public void testHttpClientException() throws MyCustomException, URISyntaxException, JsonProcessingException {
        when(restTemplate.exchange(new URI(requestUrl), HttpMethod.GET, new HttpEntity(restApiService.createHeaders(accountId, password)), Object.class)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        restApiService.getResponse(requestUrl, accountId, password);

    }

    @Test(expected = MyCustomException.class)
    public void testResourceAccessException() throws MyCustomException, URISyntaxException, JsonProcessingException {
        when(restTemplate.exchange(new URI(requestUrl), HttpMethod.GET, new HttpEntity(restApiService.createHeaders(accountId, password)), Object.class)).thenThrow(new ResourceAccessException(""));
        restApiService.getResponse(requestUrl, accountId, password);

    }

    @Test(expected = MyCustomException.class)
    public void testJsonProcessingException() throws MyCustomException, URISyntaxException, JsonProcessingException {
        when(restTemplate.exchange(new URI(requestUrl), HttpMethod.GET, new HttpEntity(restApiService.createHeaders(accountId, password)), Object.class)).thenReturn(new ResponseEntity<Object>("Hello World",HttpStatus.OK));
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("Error"){});
        restApiService.getResponse(requestUrl, accountId, password);

    }
    @Test(expected = MyCustomException.class)
    public void testURISyntaxException() throws MyCustomException, URISyntaxException, JsonProcessingException {
        final String response = "Hello World";
        restApiService.getResponse("wrong url", accountId, password);

    }
}

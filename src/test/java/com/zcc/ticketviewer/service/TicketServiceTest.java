package com.zcc.ticketviewer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.dto.GetRequestsResponse;
import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.exception.MyCustomException;
import com.zcc.ticketviewer.pojo.Secrets;
import com.zcc.ticketviewer.services.TicketService;
import com.zcc.ticketviewer.services.RestApiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    final String url = "https://zccphoenix.zendesk.com/api/v2/tickets.json?page[size]=5";

    @InjectMocks
    TicketService ticketService;

    @Mock
    RestApiService httpUtil;

    @Mock
    ObjectMapper objectMapper;

    GetTicketsResponse ticketsResponse;
    GetRequestsResponse getRequestsResponse;
    final Secrets secrets = new Secrets("hello", "world");


    @Before
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ticketsResponse = objectMapper.readValue(new File("src/test/test_files/GetTicketsResponse.json"), GetTicketsResponse.class);
        getRequestsResponse = objectMapper.readValue(new File("src/test/test_files/GetRequestsResponse.json"), GetRequestsResponse.class);
    }

    @Test
    public void getTicketsErrorAuthentication() throws MyCustomException {

        //when(secretService.getSecrets(anyString())).thenReturn(secrets);
        when(httpUtil.getResponse(url, "hello","world")).thenThrow(new MyCustomException("", 401));
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url, secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test
    public void getTicketErrorInternalServerError() throws MyCustomException {

        //when(secretService.getSecrets(anyString())).thenReturn(secrets);
        when(httpUtil.getResponse(anyString(), anyString(),anyString())).thenThrow(new MyCustomException("", 500));
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url, secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test
    public void getTicketByIdErrorAPINotAvailable() throws MyCustomException {
        when(httpUtil.getResponse(anyString(), anyString(),anyString())).thenThrow(new MyCustomException("", 503));
        GetRequestsResponse ticketsResponse = ticketService.getTicketById(url, 1,secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test
    public void getTicketErrorBadGateway() throws MyCustomException {

        //when(secretService.getSecrets(anyString())).thenReturn(secrets);
        when(httpUtil.getResponse(anyString(), anyString(),anyString())).thenThrow(new MyCustomException("", 502));
        GetRequestsResponse ticketsResponse = ticketService.getTicketById(url, 1,secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test
    public void getTicketsSuccess() throws MyCustomException, JsonProcessingException {
        when(httpUtil.getResponse(url, "hello","world")).thenReturn("");
        when(objectMapper.readValue("", GetTicketsResponse.class)).thenReturn(this.ticketsResponse);
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url, secrets);
        assertEquals(ticketsResponse, this.ticketsResponse);
    }

    @Test
    public void getRequestSuccess() throws MyCustomException, JsonProcessingException {
        when(httpUtil.getResponse(url + 1 + ".json", "hello","world")).thenReturn("");
        when(objectMapper.readValue("", GetRequestsResponse.class)).thenReturn(this.getRequestsResponse);
        GetRequestsResponse requestsResponse = ticketService.getTicketById(url,1, secrets);
        assertEquals(requestsResponse, this.getRequestsResponse);
    }

    @Test
    public void testNullSecretsForGetTickets() throws MyCustomException, JsonProcessingException {
        GetTicketsResponse requestsResponse = ticketService.getTickets(url, null);
        assertEquals(requestsResponse, null);
    }

    @Test
    public void testNullSecretsForGetTicketsByID() throws MyCustomException, JsonProcessingException {
        GetRequestsResponse requestsResponse = ticketService.getTicketById(url,1, null);
        assertEquals(requestsResponse, null);
    }

    @Test
    public void getTicketsIOException() throws MyCustomException, JsonProcessingException {
        when(httpUtil.getResponse(url, "hello","world")).thenReturn("");
        when(objectMapper.readValue("", GetTicketsResponse.class)).thenThrow(new JsonProcessingException(""){});
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url, secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test
    public void getTicketByIdIOException() throws MyCustomException, JsonProcessingException {
        when(httpUtil.getResponse(url + 1 + ".json", "hello","world")).thenReturn("");
        when(objectMapper.readValue("", GetRequestsResponse.class)).thenThrow(new JsonProcessingException(""){});
        GetRequestsResponse ticketsResponse = ticketService.getTicketById(url, 1, secrets);
        assertEquals(ticketsResponse, null);
    }




}

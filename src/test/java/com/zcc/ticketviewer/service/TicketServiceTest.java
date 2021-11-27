package com.zcc.ticketviewer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.dto.GetTicketByIdResponse;
import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.exception.ApiException;
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
    GetTicketByIdResponse getTicketByIdResponse;
    final Secrets secrets = new Secrets("hello", "world");


    @Before
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ticketsResponse = objectMapper.readValue(new File("src/test/test_files/GetTicketsResponse.json"), GetTicketsResponse.class);
        getTicketByIdResponse = objectMapper.readValue(new File("src/test/test_files/GetRequestsResponse.json"), GetTicketByIdResponse.class);
    }

    @Test(expected = ApiException.class)
    public void getTicketsErrorAuthentication() throws ApiException {

        //when(secretService.getSecrets(anyString())).thenReturn(secrets);
        when(httpUtil.getResponse(url, "hello","world")).thenThrow(new ApiException("", 401));
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url, secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test(expected = ApiException.class)
    public void getTicketErrorInternalServerError() throws ApiException {

        //when(secretService.getSecrets(anyString())).thenReturn(secrets);
        when(httpUtil.getResponse(anyString(), anyString(),anyString())).thenThrow(new ApiException("", 500));
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url, secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test(expected = ApiException.class)
    public void getTicketByIdErrorAPINotAvailable() throws ApiException {
        when(httpUtil.getResponse(anyString(), anyString(),anyString())).thenThrow(new ApiException("", 503));
        GetTicketByIdResponse ticketsResponse = ticketService.getTicketById(url, 1,secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test(expected = ApiException.class)
    public void getTicketErrorBadGateway() throws ApiException {

        //when(secretService.getSecrets(anyString())).thenReturn(secrets);
        when(httpUtil.getResponse(anyString(), anyString(),anyString())).thenThrow(new ApiException("", 502));
        GetTicketByIdResponse ticketsResponse = ticketService.getTicketById(url, 1,secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test
    public void getTicketsSuccess() throws ApiException, JsonProcessingException {
        when(httpUtil.getResponse(url, "hello","world")).thenReturn("");
        when(objectMapper.readValue("", GetTicketsResponse.class)).thenReturn(this.ticketsResponse);
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url, secrets);
        assertEquals(ticketsResponse, this.ticketsResponse);
    }

    @Test
    public void getRequestSuccess() throws ApiException, JsonProcessingException {
        when(httpUtil.getResponse(url + 1 + ".json", "hello","world")).thenReturn("");
        when(objectMapper.readValue("", GetTicketByIdResponse.class)).thenReturn(this.getTicketByIdResponse);
        GetTicketByIdResponse requestsResponse = ticketService.getTicketById(url,1, secrets);
        assertEquals(requestsResponse, this.getTicketByIdResponse);
    }

    @Test
    public void testNullSecretsForGetTickets() throws ApiException, JsonProcessingException {
        GetTicketsResponse requestsResponse = ticketService.getTickets(url, null);
        assertEquals(requestsResponse, null);
    }

    @Test
    public void testNullSecretsForGetTicketsByID() throws ApiException, JsonProcessingException {
        GetTicketByIdResponse requestsResponse = ticketService.getTicketById(url,1, null);
        assertEquals(requestsResponse, null);
    }

    @Test
    public void getTicketsIOException() throws ApiException, JsonProcessingException {
        when(httpUtil.getResponse(url, "hello","world")).thenReturn("");
        when(objectMapper.readValue("", GetTicketsResponse.class)).thenThrow(new JsonProcessingException(""){});
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url, secrets);
        assertEquals(ticketsResponse, null);
    }

    @Test
    public void getTicketByIdIOException() throws ApiException, JsonProcessingException {
        when(httpUtil.getResponse(url + 1 + ".json", "hello","world")).thenReturn("");
        when(objectMapper.readValue("", GetTicketByIdResponse.class)).thenThrow(new JsonProcessingException(""){});
        GetTicketByIdResponse ticketsResponse = ticketService.getTicketById(url, 1, secrets);
        assertEquals(ticketsResponse, null);
    }


}

package com.zcc.ticketviewer.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.pojo.Secrets;
import com.zcc.ticketviewer.services.SecretService;
import com.zcc.ticketviewer.services.TicketService;
import com.zcc.ticketviewer.util.HttpUtil;
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
    SecretService secretService;

    @Mock
    HttpUtil httpUtil;

    GetTicketsResponse ticketsResponse;


    @Before
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ticketsResponse = objectMapper.readValue(new File("src/test/test_files/GetTicketsResponse.json"), GetTicketsResponse.class);
    }

    @Test
    public void getTicketsError(){
        when(secretService.getSecrets(anyString())).thenReturn(new Secrets("hello", "world"));
        when(httpUtil.getResponse(url, "hello","world")).thenReturn(null);
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url);
        assertEquals(ticketsResponse, null);
    }

    @Test
    public void getTicketsSuccess(){
        when(secretService.getSecrets(anyString())).thenReturn(new Secrets("hello", "world"));
        when(httpUtil.getResponse(url, "hello","world")).thenReturn(ticketsResponse);
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url);
        assertEquals(ticketsResponse, this.ticketsResponse);
    }

    @Test
    public void testNullSecrets(){
        when(secretService.getSecrets(anyString())).thenReturn(null);
        GetTicketsResponse ticketsResponse = ticketService.getTickets(url);
        assertEquals(ticketsResponse, null);
    }


}

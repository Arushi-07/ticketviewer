package com.zcc.ticketviewer.services;

import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.pojo.Secrets;
import com.zcc.ticketviewer.util.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
@NoArgsConstructor
@AllArgsConstructor
public class TicketService {

    @Autowired
    private SecretService secretService;

    @Autowired
    private HttpUtil httpUtil;

    public GetTicketsResponse getTickets(String requesturl){
        final Secrets secrets = secretService.getSecrets("src/main/resources/secrets.json");
        if(secrets == null) {
            return null;
        }
        return httpUtil.getResponse(requesturl, secrets.getAccountId(), secrets.getPassword());

    }

}

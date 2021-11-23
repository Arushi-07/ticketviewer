package com.zcc.ticketviewer.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.client.ZccClient;
import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.dto.Tickets;
import com.zcc.ticketviewer.pojo.Secrets;
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

    public GetTicketsResponse getTickets(String requesturl){
        final Secrets secrets = secretService.getSecrets();
        if(secrets == null) {
            return null;
        }
        HttpURLConnection con = null;
        try {
            URL url = new URL(requesturl);
            String encoding = Base64.getEncoder().encodeToString((secrets.getAccountId()+":"+secrets.getPassword()).getBytes("UTF-8"));
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty ("Authorization", "Basic " + encoding);
            con.connect();
            int statusCode = con.getResponseCode();
            BufferedReader br;
            if(statusCode == 200){
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String response = br.lines().collect(Collectors.joining());
                ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                GetTicketsResponse responseObj = objectMapper.readValue(response, GetTicketsResponse.class);
                log.error("retrieved tickets size: " + responseObj.getTickets().size());
                return responseObj;

            } else{
                log.error("error code: " + con.getResponseMessage());

                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                return null;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

        //final List<Tickets> tickets = zccClient.getTickets();
    }

}

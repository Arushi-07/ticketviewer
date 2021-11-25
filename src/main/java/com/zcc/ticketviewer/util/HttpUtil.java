package com.zcc.ticketviewer.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.dto.GetTicketsResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.stream.Collectors;

@Log4j2
@Component
public class HttpUtil {

    public GetTicketsResponse getResponse(final String requesturl, String accountId, String password){
        try{
            HttpURLConnection con = null;
            URL url = new URL(requesturl);
            String encoding = Base64.getEncoder().encodeToString((accountId+":"+password).getBytes("UTF-8"));
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Basic " + encoding);
            con.connect();
            int statusCode = con.getResponseCode();
            BufferedReader br;
            if(statusCode == 200){
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String response = br.lines().collect(Collectors.joining());
                ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                GetTicketsResponse responseObj = objectMapper.readValue(response, GetTicketsResponse.class);
                //log.error("retrieved tickets size: " + responseObj.getTickets().size());
                return responseObj;

            } else{
                //log.error("error code: " + con.getResponseMessage());
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String err = br.lines().collect(Collectors.joining());
                switch (statusCode){
                    case 401:
                        err = "Invalid credentials(account id and password), please verify and try again";
                        break;
                    case 503:
                        err = "Zendesk API not available";
                        break;
                    case 500:
                        err = "Internal Server Error";
                        break;
                    case 502:
                        err = "Bad Gateway";
                        break;
                    case 504:
                        err = "Time out while connecting to Zendesk API";
                        break;
                    default:
                        break;
                }

                System.out.println("error: ");
                System.out.println(err);
                return null;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}

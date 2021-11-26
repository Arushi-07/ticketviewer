package com.zcc.ticketviewer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.dto.GetRequestsResponse;
import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.exception.MyCustomException;
import com.zcc.ticketviewer.pojo.Secrets;
import com.zcc.ticketviewer.util.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
@NoArgsConstructor
@AllArgsConstructor
public class TicketService {

    @Autowired
    private HttpUtil httpUtil;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * @param requesturl
     * @param secrets
     * @return
     */
    public GetTicketsResponse getTickets(final String requesturl, final Secrets secrets){
        if(secrets == null) {
            return null;
        }
        String err="";
        try{
            return objectMapper.readValue(httpUtil.getResponse(requesturl, secrets.getAccountId(), secrets.getPassword()), GetTicketsResponse.class);
        } catch(MyCustomException ex ){
            handleException(ex.getStatusCode());
            return null;


        } catch (IOException e) {
            System.out.println(err);
            return null;
        }

    }

    public GetRequestsResponse getTicketById(final String requesturl, final int id, final Secrets secrets){
        if(secrets == null) {
            return null;
        }
        String err="";
        try{
            return objectMapper.readValue(httpUtil.getResponse(requesturl + id+ ".json", secrets.getAccountId(), secrets.getPassword()), GetRequestsResponse.class);
        } catch(MyCustomException ex ){
            handleException(ex.getStatusCode());
            return null;


        } catch (IOException e) {
            System.out.println(err);
            return null;
        }

    }


    public void handleException(int statusCode){
        String err = "";
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
        System.out.println(err);
    }

}

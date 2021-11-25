package com.zcc.ticketviewer.services;

import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.pojo.Secrets;
import com.zcc.ticketviewer.util.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@NoArgsConstructor
@AllArgsConstructor
public class TicketService {

    @Autowired
    private SecretService secretService;

    @Autowired
    private HttpUtil httpUtil;

    public GetTicketsResponse getTickets(final String requesturl, final Secrets secrets){
        // final Secrets secrets = secretService.getSecrets("src/main/resources/secrets.json");
        if(secrets == null) {
            return null;
        }
        return httpUtil.getResponse(requesturl, secrets.getAccountId(), secrets.getPassword());

    }

}

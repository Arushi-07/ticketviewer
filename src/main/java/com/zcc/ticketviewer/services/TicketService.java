package com.zcc.ticketviewer.services;

import com.zcc.ticketviewer.client.ZccClient;
import com.zcc.ticketviewer.dto.Tickets;
import com.zcc.ticketviewer.pojo.Secrets;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@NoArgsConstructor
@AllArgsConstructor
public class TicketService {

    @Autowired
    private SecretService secretService;

//    @Autowired
//    private ZccClient zccClient;

    public void getTickets(final Integer userId){
        final Secrets secrets = secretService.getSecrets();
        if(secrets == null){
            return;
        }
        log.error("account id  : " + secrets.getAccountId());
        log.error("password  : " + secrets.getPassword());
        //final List<Tickets> tickets = zccClient.getTickets();
    }

}

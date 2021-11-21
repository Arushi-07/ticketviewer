package com.zcc.ticketviewer.client;

import com.zcc.ticketviewer.dto.Tickets;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "ZCC", url = "${zcc.url}")
public interface ZccClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v2/tickets.json")
    List<Tickets> getTickets();

}

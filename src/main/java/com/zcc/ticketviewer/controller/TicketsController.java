package com.zcc.ticketviewer.controller;

import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.dto.Greeting;
import com.zcc.ticketviewer.dto.Tickets;
import com.zcc.ticketviewer.services.TicketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Log4j2
@Controller
public class TicketsController {
    @Autowired
    private TicketService ticketService;

//    @GetMapping("/tickets")
//    public String greetingForm(Model model) {
//        final List<Tickets> tickets = ticketService.getTickets();
//        log.error("ticket here: " + tickets.size());
//        log.error("ticket here: " + tickets.get(0).getCreatedAt());
//        model.addAttribute("tickets", tickets);
//        return "tickets";
//    }
//
//    @PostMapping("/greeting2")
//    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
//        model.addAttribute("greeting", greeting);
//        final List<Tickets> tickets = ticketService.getTickets();
//        log.error("ticket here: " + tickets.size());
//        log.error("ticket here: " + tickets.get(0).getCreatedAt());
//        model.addAttribute("tickets", tickets);
//        return "tickets";
//    }

}

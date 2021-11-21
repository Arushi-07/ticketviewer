package com.zcc.ticketviewer.controller;

import com.zcc.ticketviewer.dto.Greeting;
import com.zcc.ticketviewer.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TicketsController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public String greetingForm(Model model) {
        ticketService.getTickets(1);
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

}

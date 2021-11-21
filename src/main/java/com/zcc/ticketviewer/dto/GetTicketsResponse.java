package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetTicketsResponse {

    @JsonProperty("tickets")
    List<Tickets> tickets;
}

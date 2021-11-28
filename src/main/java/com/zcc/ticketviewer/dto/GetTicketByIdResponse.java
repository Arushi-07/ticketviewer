package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The response of the get requests API of zendesk
 */
@Getter
@Setter
public class GetTicketByIdResponse {

    @JsonProperty("ticket")
    Tickets ticket;
}
package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * The response of the get tickets API of zendesk
 */
@Getter
@Setter
public class GetTicketsResponse {

    @JsonProperty("tickets")
    List<Tickets> tickets;

    @JsonProperty("meta")
    MetaData meta;

    @JsonProperty("links")
    LinksData links;
}

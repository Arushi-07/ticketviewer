package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The Links component of the get tickets API response of zendesk
 */
@Getter
@Setter
public class LinksData {
    @JsonProperty("next")
    private String next;

    @JsonProperty("prev")
    private String prev;
}

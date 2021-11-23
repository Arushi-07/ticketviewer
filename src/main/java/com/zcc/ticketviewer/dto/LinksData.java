package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinksData {
    @JsonProperty("next")
    private String next;

    @JsonProperty("prev")
    private String prev;
}

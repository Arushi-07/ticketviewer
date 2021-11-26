package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRequestsResponse {

    @JsonProperty("request")
    Tickets request;
}
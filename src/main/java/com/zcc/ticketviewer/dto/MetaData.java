package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * The Meta component of the get tickets API response of zendesk
 */
@Getter
@Setter
public class MetaData {

    @JsonProperty("has_more")
    Boolean hasMore;


}

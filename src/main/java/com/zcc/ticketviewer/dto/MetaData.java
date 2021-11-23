package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaData {

    @JsonProperty("has_more")
    boolean hasMore;

    public boolean getHasMore() {
        return this.hasMore;
    }
}

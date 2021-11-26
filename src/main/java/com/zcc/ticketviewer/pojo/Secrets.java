package com.zcc.ticketviewer.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Secrets {

    @JsonProperty("account_id")
    @NonNull
    public String accountId;

    @JsonProperty("password")
    @NonNull
    public String password;
}

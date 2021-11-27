package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * The list of tickets in the API response of zendesk
 */
@Getter
@Setter
public class Tickets {

    @JsonProperty("id")
    String id;

    @JsonProperty("subject")
    String subject;

    @JsonProperty("requester_id")
    String requestedId;

    @JsonProperty("created_at")
    String createdAt;

    @JsonProperty("description")
    String description;

    @JsonProperty("due_at")
    String dueAt;

    @JsonProperty("external_id")
    String externalId;

    @JsonProperty("group_id")
    String groupId;

    @JsonProperty("organization_id")
    String organizationId;

    @JsonProperty("priority")
    String priority;

    @JsonProperty("status")
    String status;

    @JsonProperty("type")
    String type;

    @JsonProperty("url")
    String url;


}

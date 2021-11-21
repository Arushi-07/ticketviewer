package com.zcc.ticketviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tickets {

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("description")
    private String description;

    @JsonProperty("due_at")
    private String dueAt;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("group_id")
    private String groupId;

    @JsonProperty("organization_id")
    private String organizationId;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("status")
    private String status;

    @JsonProperty("type")
    private String type;

    @JsonProperty("url")
    private String url;
}

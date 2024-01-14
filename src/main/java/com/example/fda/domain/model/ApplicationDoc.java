package com.example.fda.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationDoc {
    @JsonProperty("application_docs_id")
    private String applicationDocsId;
    @JsonProperty("application_docs_date")
    private String applicationDocsDate;
    @JsonProperty("application_docs_title")
    private String applicationDocsTitle;
    @JsonProperty("application_docs_type")
    private String applicationDocsType;
    @JsonProperty("application_docs_url")
    private String applicationDocsUrl;
}

package com.example.fda.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Submission {
    @JsonProperty("submission_number")
    private String submissionNumber;
    @JsonProperty("submission_type")
    private String submissionType;
    @JsonProperty("submission_status")
    private String submissionStatus;
    @JsonProperty("submission_status_date")
    private String submissionStatusDate;
    @JsonProperty("review_priority")
    private String reviewPriority;
    @JsonProperty("application_docs")
    private List<ApplicationDoc> applicationDocs;
    @JsonProperty("submission_class_code")
    private String submissionClassCode;
    @JsonProperty("submission_class_code_description")
    private String submissionClassCodeDescription;
    @JsonProperty("submission_public_notes")
    private String submissionPublicNotes;
    @JsonProperty("submission_property_type")
    private List<SubmissionPropertyType> submissionPropertyType;
}
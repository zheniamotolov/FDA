package com.example.fda.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class DrugRecord {
    @JsonProperty("application_number")
    private String applicationNumber;
    @JsonProperty("sponsor_name")
    private String sponsorName;
    private List<Product> products;
    private OpenFda openfda;
    private List<Submission> submissions;

}

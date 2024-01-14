package com.example.fda.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFda {
    @JsonProperty("application_number")
    private List<String> applicationNumbers;
    @JsonProperty("brand_name")
    private List<String> brandNames;
    @JsonProperty("generic_name")
    private List<String> genericNames;
    @JsonProperty("manufacturer_name")
    private List<String> manufacturerNames;
    @JsonProperty("nui")
    private List<String> nuis;
    @JsonProperty("package_ndc")
    private List<String> packageNdcs;
    @JsonProperty("pharm_class_cs")
    private List<String> pharmClassCs;
    @JsonProperty("pharm_class_epc")
    private List<String> pharmClassEpcs;
    @JsonProperty("pharm_class_pe")
    private List<String> pharmClassPes;
    @JsonProperty("pharm_class_moa")
    private List<String> pharmClassMoas;
    @JsonProperty("product_ndc")
    private List<String> productNdcs;
    @JsonProperty("route")
    private List<String> routes;
    @JsonProperty("rxcui")
    private List<String> rxcuis;
    @JsonProperty("spl_id")
    private List<String> splIds;
    @JsonProperty("spl_set_id")
    private List<String> splSetIds;
    @JsonProperty("substance_name")
    private List<String> substanceNames;
    @JsonProperty("unii")
    private List<String> uniis;
}

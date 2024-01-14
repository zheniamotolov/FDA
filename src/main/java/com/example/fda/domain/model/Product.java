package com.example.fda.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @JsonProperty("product_number")
    private String productNumber;
    @JsonProperty("dosage_form")
    private String dosageForm;
    @JsonProperty("brand_name")
    private String brandName;
    @JsonProperty("marketing_status")
    private String marketingStatus;
    @JsonProperty("reference_drug")
    private String referenceDrug;
    @JsonProperty("reference_standard")
    private String referenceStandard;
    private String route;
    @JsonProperty("te_code")
    private String teCode;
    @JsonProperty("active_ingredients")
    private List<ActiveIngredient> activeIngredients;
}

package com.example.fda.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugRecordDetailsDto {
    @NotBlank
    @Size(min = 1, max = 100)
    @JsonProperty("application_number")
    private String applicationNumber;
    @JsonProperty("manufacturer_name")
    private List<@Size(min = 1, max = 100) String> manufacturerName;
    @JsonProperty("substance_name")
    private List<@Size(min = 1, max = 100) String> substanceName;
    @JsonProperty("product_number")
    private List<@Size(min = 1, max = 100) String> productNumber;
}

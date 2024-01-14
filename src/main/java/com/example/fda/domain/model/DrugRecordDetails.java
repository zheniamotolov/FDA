package com.example.fda.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
public class DrugRecordDetails {
    @Id
    @Indexed(unique = true)
    private String applicationNumber;
    private List<String> manufacturerName;
    private List<String> substanceName;
    private List<String> productNumber;
}

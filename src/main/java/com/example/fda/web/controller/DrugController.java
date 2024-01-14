package com.example.fda.web.controller;

import com.example.fda.domain.dto.DrugRecordDetailsDto;
import com.example.fda.domain.model.DrugRecord;
import com.example.fda.service.DrugRecordDetailsService;
import com.example.fda.service.DrugRecordService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {
    private final DrugRecordService drugRecordService;
    private final DrugRecordDetailsService drugRecordDetailsService;

    @GetMapping()
    @Operation(summary = "Search drug application record in FDA by manufactureName and brandName")
    public ResponseEntity<List<DrugRecord>> findDrugRecords(@PageableDefault() Pageable pageable,
                                                            @RequestParam(value = "manufactureName", defaultValue = "", required = false) String manufactureName,
                                                            @RequestParam(value = "brandName", defaultValue = "", required = false) String brandName) {
        return ResponseEntity.ok(drugRecordService.findDrugRecords(pageable, manufactureName, brandName));
    }

    @GetMapping("details")
    @Operation(summary = "Show drug record applications stored in the system")
    public ResponseEntity<Page<DrugRecordDetailsDto>> findStoredDrugRecordDetails(@PageableDefault() Pageable pageable,
                                                                                  @RequestParam(value = "applicationNumber", defaultValue = "", required = false) String applicationNumber) {
        return ResponseEntity.ok(drugRecordDetailsService.findDrugRecordDetails(pageable, applicationNumber));
    }

    @PostMapping("/details")
    @Operation(summary = "Store specific drug record applications details")
    public ResponseEntity<DrugRecordDetailsDto> saveDrugRecordDetails(@Valid @RequestBody DrugRecordDetailsDto DrugRecordDetailsDto) {
        return ResponseEntity.ok(drugRecordDetailsService.saveDrugRecordDetails(DrugRecordDetailsDto));
    }
}

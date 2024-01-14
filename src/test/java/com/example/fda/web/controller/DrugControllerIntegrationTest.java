package com.example.fda.web.controller;

import com.example.fda.domain.dto.DrugRecordDetailsDto;
import com.example.fda.domain.model.DrugRecord;
import com.example.fda.domain.model.OpenFda;
import com.example.fda.service.DrugRecordDetailsService;
import com.example.fda.service.DrugRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DrugController.class)
public class DrugControllerIntegrationTest {

    private static final String TEST_BRAND = "testBrand";
    private static final String TEST_MANUFACTURER_NAME = "testManufacturer";
    private static final String TEST_APPLICATION_NUMBER = "testApplicationNumber";
    private static final String TEST_SPONSOR_NAME = "testSponsor";
    private static final String TEST_SUBSTANCE_NAME = "testSubstance";
    private static final String TEST_PRODUCT_NUMBER = "testProduct";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DrugRecordService drugRecordService;

    @MockBean
    private DrugRecordDetailsService drugRecordDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFindDrugRecords() throws Exception {
        int page = 0;
        int size = 10;

        OpenFda openFda = new OpenFda();
        openFda.setBrandNames(List.of(TEST_BRAND));
        openFda.setManufacturerNames(List.of(TEST_MANUFACTURER_NAME));

        DrugRecord mockDrugRecord = new DrugRecord();
        mockDrugRecord.setApplicationNumber(TEST_APPLICATION_NUMBER);
        mockDrugRecord.setSponsorName(TEST_SPONSOR_NAME);
        mockDrugRecord.setOpenfda(openFda);

        List<DrugRecord> drugRecordList = List.of(mockDrugRecord);

        when(drugRecordService.findDrugRecords(page, size, TEST_MANUFACTURER_NAME, TEST_BRAND))
                .thenReturn(drugRecordList);

        mockMvc.perform(get("/api/drugs")
                        .param("manufactureName", TEST_MANUFACTURER_NAME)
                        .param("brandName", TEST_BRAND)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].application_number").value(TEST_APPLICATION_NUMBER))
                .andExpect(jsonPath("$[0].sponsor_name").value(TEST_SPONSOR_NAME))
                .andExpect(jsonPath("$[0].openfda.brand_name[0]").value(TEST_BRAND))
                .andExpect(jsonPath("$[0].openfda.manufacturer_name[0]").value(TEST_MANUFACTURER_NAME));
    }

    @Test
    void shouldSaveDrugRecordDetails() throws Exception {
        DrugRecordDetailsDto drugRecordDetailsDto = new DrugRecordDetailsDto();
        drugRecordDetailsDto.setApplicationNumber(TEST_APPLICATION_NUMBER);
        drugRecordDetailsDto.setManufacturerName(List.of(TEST_MANUFACTURER_NAME));
        drugRecordDetailsDto.setSubstanceName(List.of(TEST_SUBSTANCE_NAME));
        drugRecordDetailsDto.setProductNumber(List.of(TEST_PRODUCT_NUMBER));

        when(drugRecordDetailsService.saveDrugRecordDetails(any(DrugRecordDetailsDto.class)))
                .thenReturn(drugRecordDetailsDto);

        mockMvc.perform(post("/api/drugs/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(drugRecordDetailsDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.application_number").value(TEST_APPLICATION_NUMBER))
                .andExpect(jsonPath("$.manufacturer_name").value(TEST_MANUFACTURER_NAME))
                .andExpect(jsonPath("$.substance_name").value(TEST_SUBSTANCE_NAME))
                .andExpect(jsonPath("$.product_number").value(TEST_PRODUCT_NUMBER));
    }

    @Test
    void shouldFindStoredDrugRecordDetails() throws Exception {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        DrugRecordDetailsDto mockDrugRecordDetailsDto = new DrugRecordDetailsDto(
                TEST_APPLICATION_NUMBER,
                List.of(TEST_MANUFACTURER_NAME),
                List.of(TEST_SUBSTANCE_NAME),
                List.of(TEST_PRODUCT_NUMBER)
        );

        List<DrugRecordDetailsDto> dtoList = List.of(mockDrugRecordDetailsDto);
        Page<DrugRecordDetailsDto> detailsDtoPage = new PageImpl<>(dtoList, pageable, dtoList.size());
        when(drugRecordDetailsService.findDrugRecordDetails(pageable, TEST_APPLICATION_NUMBER))
                .thenReturn(detailsDtoPage);
        mockMvc.perform(get("/api/drugs/details")
                        .param("applicationNumber", TEST_APPLICATION_NUMBER)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].application_number").value(TEST_APPLICATION_NUMBER))
                .andExpect(jsonPath("$.content[0].manufacturer_name[0]").value(TEST_MANUFACTURER_NAME))
                .andExpect(jsonPath("$.content[0].substance_name[0]").value(TEST_SUBSTANCE_NAME))
                .andExpect(jsonPath("$.content[0].product_number[0]").value(TEST_PRODUCT_NUMBER));
    }

    @Test
    void shouldHandleFindDrugRecordsException() throws Exception {
        when(drugRecordService.findDrugRecords(anyInt(), anyInt(), anyString(), anyString()))
                .thenThrow(new RestClientException("External API is not available"));

        mockMvc.perform(get("/api/drugs")
                        .param("manufactureName", "unavailableManufacturer")
                        .param("brandName", "unavailableBrand"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error fetching data from external API"))
                .andExpect(jsonPath("$.errors[0]").value("External API is not available"));
    }

    @Test
    void shouldHandleDrugRecordDetailsValidationException() throws Exception {
        DrugRecordDetailsDto incompleteDto = new DrugRecordDetailsDto();

        mockMvc.perform(post("/api/drugs/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incompleteDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));
    }

}
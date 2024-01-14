package com.example.fda.client;

import com.example.fda.domain.model.DrugRecord;
import com.example.fda.domain.model.FdaDrugResponse;
import com.example.fda.domain.model.OpenFda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FdaApiClientTest {
    private final String TEST_BRAND_NAME = "testBrand";
    private final String TEST_MANUFACTURER_NAME = "testManufacturer";
    private final String TEST_APPLICATION_NUMBER = "testApplicationNumber";
    private final String TEST_API_URL = "http://example.com/api";

    private final int SKIP = 0;
    private final int LIMIT = 5;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FdaApiClient fdaApiClient;

    @BeforeEach
    void setUp() {
        fdaApiClient = new FdaApiClient(restTemplate);
        ReflectionTestUtils.setField(fdaApiClient, "fdaDrugApiUrl", TEST_API_URL);
    }

    @Test
    void shouldSearchDrugsByManufactureAndBrandName() {
        DrugRecord drugRecord= new DrugRecord();
        OpenFda openFda = new OpenFda();
        openFda.setManufacturerNames(List.of(TEST_MANUFACTURER_NAME));
        openFda.setBrandNames(List.of(TEST_BRAND_NAME));
        drugRecord.setOpenfda(openFda);
        drugRecord.setApplicationNumber(TEST_APPLICATION_NUMBER);

        List<DrugRecord> expectedRecords = Collections.singletonList(drugRecord);
        FdaDrugResponse mockResponse = new FdaDrugResponse(expectedRecords);

        when(restTemplate.getForEntity(
                "http://example.com/api?search=openfda.manufacturer_name:" + TEST_MANUFACTURER_NAME
                        + "+AND+openfda.brand_name:" + TEST_BRAND_NAME + "&skip="+SKIP+"&limit="+LIMIT,
                FdaDrugResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        List<DrugRecord> results = fdaApiClient.searchDrugsByManufactureAndBrandName(TEST_MANUFACTURER_NAME, TEST_BRAND_NAME, SKIP, LIMIT);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(expectedRecords, results);
    }

    @Test
    void shouldSearchDrugsByManufacture() {
        DrugRecord drugRecord= new DrugRecord();
        OpenFda openFda = new OpenFda();
        openFda.setManufacturerNames(List.of(TEST_MANUFACTURER_NAME));
        openFda.setBrandNames(List.of(TEST_BRAND_NAME));
        drugRecord.setOpenfda(openFda);
        drugRecord.setApplicationNumber(TEST_APPLICATION_NUMBER);

        List<DrugRecord> expectedRecords = Collections.singletonList(drugRecord);
        FdaDrugResponse mockResponse = new FdaDrugResponse(expectedRecords);

        when(restTemplate.getForEntity(
                "http://example.com/api?search=openfda.manufacturer_name:" + TEST_MANUFACTURER_NAME + "&skip="+SKIP+"&limit="+LIMIT,
                FdaDrugResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        List<DrugRecord> results = fdaApiClient.searchDrugsByManufactureAndBrandName(TEST_MANUFACTURER_NAME, null, SKIP, LIMIT);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(expectedRecords, results);
    }

    @Test
    void whenSearchDrugsByManufactureAndBrandName_thenHttpExceptionThrown() {
        when(restTemplate.getForEntity(
                "http://example.com/api?search=openfda.manufacturer_name:" + TEST_MANUFACTURER_NAME
                        + "+AND+openfda.brand_name:" + TEST_BRAND_NAME + "&skip="+ SKIP +"&limit="+ LIMIT,
                FdaDrugResponse.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(RestClientException.class, () ->
                fdaApiClient.searchDrugsByManufactureAndBrandName(TEST_MANUFACTURER_NAME, TEST_BRAND_NAME, SKIP, LIMIT));
    }

    @Test
    void whenSearchDrugsByManufactureAndBrandName_thenRestClientExceptionThrown() {
        when(restTemplate.getForEntity("http://example.com/api?search=openfda.manufacturer_name:" + TEST_MANUFACTURER_NAME
                + "+AND+openfda.brand_name:" + TEST_BRAND_NAME + "&skip=0&limit=5", FdaDrugResponse.class))
                .thenThrow(new RestClientException("Error occurred"));

        assertThrows(RestClientException.class, () ->
                fdaApiClient.searchDrugsByManufactureAndBrandName(TEST_MANUFACTURER_NAME, TEST_BRAND_NAME, SKIP, LIMIT));
    }
}
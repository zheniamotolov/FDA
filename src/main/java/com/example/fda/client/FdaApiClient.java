package com.example.fda.client;

import com.example.fda.domain.model.DrugRecord;
import com.example.fda.domain.model.FdaDrugResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FdaApiClient {
    private static final String SEARCH = "search";
    private static final String SKIP = "skip";
    private static final String LIMIT = "limit";
    private static final String MANUFACTURER_NAME = "openfda.manufacturer_name";
    private static final String BRAND_NAME = "openfda.brand_name";
    @Value("${fda.drug.api.url}")
    private String fdaDrugApiUrl;

    private final RestTemplate restTemplate;

    public List<DrugRecord> searchDrugsByManufactureAndBrandName(String manufactureName, String brandName, int skip, int limit) {
        String requestUrl = buildUrl(manufactureName, brandName, skip, limit);

        try {
            ResponseEntity<FdaDrugResponse> fdaDrugResponse = restTemplate.getForEntity(requestUrl, FdaDrugResponse.class);
            if (fdaDrugResponse.getBody() != null) {
                return fdaDrugResponse.getBody().getResults();
            } else {
                throw new RestClientException("Failed to fetch drug records from FDA API");
            }
        } catch (Exception ex) {
            log.error("Error occurred when calling FDA API: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    private String buildUrl(String manufactureName, String brandName, int skip, int limit) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(fdaDrugApiUrl)
                .queryParam(SEARCH, constructSearchQuery(manufactureName, brandName))
                .queryParam(SKIP, skip)
                .queryParam(LIMIT, limit);
        String requestUrl = builder.toUriString();
        return requestUrl;
    }

    private String constructSearchQuery(String manufactureName, String brandName) {
        StringBuilder searchQuery = new StringBuilder();

        if (manufactureName != null && !manufactureName.isBlank()) {
            searchQuery.append(MANUFACTURER_NAME + ":").append(manufactureName);
        }

        if (brandName != null && !brandName.isBlank()) {
            if (!searchQuery.isEmpty()) {
                searchQuery.append("+AND+");
            }
            searchQuery.append(BRAND_NAME + ":").append(brandName);
        }

        return searchQuery.toString();
    }


}

package com.example.fda.service;

import com.example.fda.client.FdaApiClient;
import com.example.fda.domain.model.DrugRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugRecordService {

    private final FdaApiClient fdaApiClient;

    public List<DrugRecord> findDrugRecords(int page, int size, String manufactureName, String brandName) {
        int skip = page * size;
        int limit = size;
        return fdaApiClient.searchDrugsByManufactureAndBrandName(manufactureName, brandName, skip, limit);
    }


}

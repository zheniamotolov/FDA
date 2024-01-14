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

    public List<DrugRecord> findDrugRecords(Pageable pageable, String manufactureName, String brandName) {
        int skip = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        return fdaApiClient.searchDrugsByManufactureAndBrandName(manufactureName, brandName, skip, limit);
    }


}

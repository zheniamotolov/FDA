package com.example.fda.service;

import com.example.fda.domain.dto.DrugRecordDetailsDto;
import com.example.fda.domain.model.DrugRecordDetails;
import com.example.fda.mapper.DrugRecordDetailsMapper;
import com.example.fda.repository.DrugRecordDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrugRecordDetailsService {

    private final DrugRecordDetailsRepository drugRecordDetailsRepository;
    private final DrugRecordDetailsMapper drugRecordDetailsMapper;

    public Page<DrugRecordDetailsDto> findDrugRecordDetails(Pageable pageable, String applicationNumber) {
        Page<DrugRecordDetails> page;

        if (applicationNumber != null && !applicationNumber.isEmpty()) {
            page = drugRecordDetailsRepository.findByApplicationNumberContainingIgnoreCase(applicationNumber, pageable);
        } else {
            page = drugRecordDetailsRepository.findAll(pageable);
        }

        return page.map(drugRecordDetailsMapper::toDto);
    }

    public DrugRecordDetailsDto saveDrugRecordDetails(DrugRecordDetailsDto drugRecordDetailsDto) {
        boolean exists = drugRecordDetailsRepository.existsByApplicationNumber(drugRecordDetailsDto.getApplicationNumber());

        if (exists) {
            throw new DataIntegrityViolationException("A record with the same application number already exists");
        }

        DrugRecordDetails entity = drugRecordDetailsMapper.toEntity(drugRecordDetailsDto);
        DrugRecordDetails savedEntity = drugRecordDetailsRepository.save(entity);
        return drugRecordDetailsMapper.toDto(savedEntity);
    }
}

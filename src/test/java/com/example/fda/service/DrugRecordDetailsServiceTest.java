package com.example.fda.service;

import com.example.fda.domain.dto.DrugRecordDetailsDto;
import com.example.fda.domain.model.DrugRecordDetails;
import com.example.fda.mapper.DrugRecordDetailsMapper;
import com.example.fda.repository.DrugRecordDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DrugRecordDetailsServiceTest {

    @Mock
    private DrugRecordDetailsRepository drugRecordDetailsRepository;

    @Mock
    private DrugRecordDetailsMapper drugRecordDetailsMapper;

    @InjectMocks
    private DrugRecordDetailsService drugRecordDetailsService;

    private DrugRecordDetailsDto drugRecordDetailsDto;
    private DrugRecordDetails drugRecordDetails;

    @BeforeEach
    void setUp() {
        drugRecordDetailsDto = new DrugRecordDetailsDto(
                "testApplicationNumber",
                List.of("testManufacturer"),
                List.of("testSubstance"),
                List.of("testProduct")
        );

        drugRecordDetails = new DrugRecordDetails(
                "testApplicationNumber",
                List.of("testManufacturer"),
                List.of("testSubstance"),
                List.of("testProduct")
        );
    }

    @Test
    void shouldSaveDrugRecordDetails() {
        when(drugRecordDetailsRepository.existsByApplicationNumber(drugRecordDetailsDto.getApplicationNumber())).thenReturn(false);
        when(drugRecordDetailsRepository.save(any(DrugRecordDetails.class))).thenReturn(drugRecordDetails);
        when(drugRecordDetailsMapper.toDto(any(DrugRecordDetails.class))).thenReturn(drugRecordDetailsDto);
        when(drugRecordDetailsMapper.toEntity(any(DrugRecordDetailsDto.class))).thenReturn(drugRecordDetails);

        DrugRecordDetailsDto result = drugRecordDetailsService.saveDrugRecordDetails(drugRecordDetailsDto);

        assertNotNull(result);
        assertEquals(drugRecordDetailsDto.getApplicationNumber(), result.getApplicationNumber());
    }

    @Test
    void shouldThrowExceptionWhenSaveDrugRecordDetails() {
        when(drugRecordDetailsRepository.existsByApplicationNumber(drugRecordDetailsDto.getApplicationNumber())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> drugRecordDetailsService.saveDrugRecordDetails(drugRecordDetailsDto));
    }

    @Test
    void shouldFindDrugRecordDetailsByAppNumber() {
        Page<DrugRecordDetails> drugRecordDetailsPage = new PageImpl<>(List.of(drugRecordDetails));
        when(drugRecordDetailsRepository.findByApplicationNumberContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(drugRecordDetailsPage);
        when(drugRecordDetailsMapper.toDto(any(DrugRecordDetails.class)))
                .thenReturn(drugRecordDetailsDto);

        Page<DrugRecordDetailsDto> result = drugRecordDetailsService.findDrugRecordDetails(Pageable.unpaged(), "testApplicationNumber");

        assertEquals(1, result.getContent().size());
        assertEquals(drugRecordDetailsDto, result.getContent().get(0));
    }

    @Test
    void shouldFindDrugRecordDetailsAll() {
        Page<DrugRecordDetails> drugRecordDetailsPage = new PageImpl<>(List.of(drugRecordDetails));
        when(drugRecordDetailsRepository.findAll(any(Pageable.class))).thenReturn(drugRecordDetailsPage);
        when(drugRecordDetailsMapper.toDto(any(DrugRecordDetails.class)))
                .thenReturn(drugRecordDetailsDto);

        Page<DrugRecordDetailsDto> result = drugRecordDetailsService.findDrugRecordDetails(Pageable.unpaged(), null);

        assertEquals(1, result.getContent().size());
        assertEquals(drugRecordDetailsDto, result.getContent().get(0));
    }
}
package com.example.fda.mapper;

import com.example.fda.domain.dto.DrugRecordDetailsDto;
import com.example.fda.domain.model.DrugRecordDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DrugRecordDetailsMapper {
//    DrugRecordDetailsMapper INSTANCE = Mappers.getMapper(DrugRecordDetailsMapper.class);

    DrugRecordDetails toEntity(DrugRecordDetailsDto dto);

    DrugRecordDetailsDto toDto(DrugRecordDetails entity);
}

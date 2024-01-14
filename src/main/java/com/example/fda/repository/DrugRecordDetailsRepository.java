package com.example.fda.repository;

import com.example.fda.domain.model.DrugRecordDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRecordDetailsRepository extends MongoRepository<DrugRecordDetails, String> {

    Page<DrugRecordDetails> findByApplicationNumberContainingIgnoreCase(String applicationNumber, Pageable pageable);

    boolean existsByApplicationNumber(String applicationNumber);
}
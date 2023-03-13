package com.spring.mongodb.services;

import com.spring.mongodb.models.Inspections;
import com.spring.mongodb.repositories.InspectionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionsService {
    private final InspectionsRepository inspectionsRepository;

    public List<Inspections> getAll() {
        var listInspections = inspectionsRepository.findAll();
        return listInspections;
    }

    public List<Inspections> getAllBySector(String sector) {
        var listInspections = inspectionsRepository.findAllBySector(sector);
        return  listInspections;
    }

    public Inspections getByBusinessName(String businessName) {
        var inspections = inspectionsRepository.findInspectionsByBusinessName(businessName);
        return  inspections;
    }
}

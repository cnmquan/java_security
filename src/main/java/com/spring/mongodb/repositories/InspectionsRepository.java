package com.spring.mongodb.repositories;

import com.spring.mongodb.models.Inspections;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InspectionsRepository extends MongoRepository<Inspections, String> {
    @Query("{business_name:'?0'}")
    Inspections findInspectionsByBusinessName(String businessName);

    @Query(value = "{sector:  '?0'}", fields = "{'business_name':  1, 'result':  1, 'address':  1}")
    List<Inspections> findAllBySector(String sector);
}

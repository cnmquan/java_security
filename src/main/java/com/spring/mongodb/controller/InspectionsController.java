package com.spring.mongodb.controller;

import com.spring.mongodb.assemblers.InspectionsAssembler;
import com.spring.mongodb.models.Inspections;
import com.spring.mongodb.services.InspectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/inspections")
@RequiredArgsConstructor
public class InspectionsController {
    private final InspectionsService service;

    private final InspectionsAssembler assembler;

    @GetMapping()
    public CollectionModel<EntityModel<Inspections>> getAll(@RequestParam Optional<String> sector) {
        List<Inspections> inspectionsList;
        if(sector.isPresent()){
            inspectionsList = service.getAllBySector(sector.get());
        } else {
            inspectionsList = service.getAll();
        }
        List<EntityModel<Inspections>> inspectionsEntities = inspectionsList.stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(
                inspectionsEntities,
                linkTo(
                        methodOn(InspectionsController.class).getAll(Optional.empty())
                ).withSelfRel()
        );
    }

    @GetMapping("/{businessName}")
    public EntityModel<Inspections> getByBusinessName(@PathVariable String businessName){
        Inspections inspections = service.getByBusinessName(businessName);
        return assembler.toModel(inspections);
    }
}

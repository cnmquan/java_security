package com.spring.mongodb.assemblers;

import com.spring.mongodb.controller.InspectionsController;
import com.spring.mongodb.models.Inspections;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InspectionsAssembler implements RepresentationModelAssembler<Inspections, EntityModel<Inspections>> {
    @Override
    public EntityModel<Inspections> toModel(Inspections entity) {
        EntityModel<Inspections> inspectionsEntityModel = EntityModel.of(entity,
                linkTo(methodOn(InspectionsController.class).getByBusinessName(entity.getBusinessName())).withSelfRel(),
                linkTo(methodOn(InspectionsController.class).getAll(null)).withRel("inspections"));
        return inspectionsEntityModel;
    }
}

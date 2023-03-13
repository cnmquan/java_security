package com.spring.mongodb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("inspections")
public class Inspections {
    @Id
    private String id;
    @Field("certificate_number")
    private String certificateNumber;
    @Field("business_name")
    private String businessName;
    @Field("date")
    private String date;
    @Field("result")
    private String result;
    @Field("sector")
    private String sector;
    @Field("address")
    private Address address;
}

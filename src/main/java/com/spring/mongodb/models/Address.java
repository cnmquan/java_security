package com.spring.mongodb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Field("city")
    private String city;
    @Field("zip")
    private String zip;
    @Field("street")
    private String street;
    @Field("number")
    private String number;
}

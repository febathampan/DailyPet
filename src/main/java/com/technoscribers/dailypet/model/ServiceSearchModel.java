package com.technoscribers.dailypet.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ServiceSearchModel {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String imageURL;
    private String serviceType;
    List<PWAvailabilityModel> pwAvailabilities;
}

package com.technoscribers.dailypet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ServiceSearchDetailModel {
    private ServiceSearchModel service;
    private List<PWAvailabilityModel> pwAvailabilities;
}

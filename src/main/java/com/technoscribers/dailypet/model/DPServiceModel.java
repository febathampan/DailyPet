package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DPServiceModel {
    private String name;
    private String phone;
    private ServiceType type;
    private String address;
    private String city;
    private String province;
    private String pinCode;
}

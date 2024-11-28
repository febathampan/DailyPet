package com.technoscribers.dailypet.model;

import lombok.Data;

@Data
public class UserAddressModel {
    private Long userId;
    private String address;
    private String city;
    private String province;
    private String postCode;
}

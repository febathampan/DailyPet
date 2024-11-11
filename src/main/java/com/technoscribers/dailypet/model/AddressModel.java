package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.ServiceType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressModel {
    private Long serviceId;
    private Long pwPersonId;
    private String address;
    private String city;
    private String province;
    private String pinCode;
}

package com.technoscribers.dailypet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileModel {
    private Long serviceId;
    private Long pwPersonId;
    private String name;
    private String lName;
    private String phone;
}

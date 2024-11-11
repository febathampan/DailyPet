package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DPPersonModel {
    private Long id;
    private String fName;
    private String lName;
    private String phone;
    private Sex gender;
    private String address;
    private String city;
    private String province;
    private String pincode;
    private Date dob;
    private String imageURL;

}

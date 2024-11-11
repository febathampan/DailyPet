package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
//Table - Person
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fname;
    private String lname;
    private String phone;
    private String gender;
    private String address;
    private String city;
    private String pincode;
    private String province;
    private String imageURL;
    private Date dob;
    @OneToOne
    @NotNull
    private User user;

    public Person(String fname, String lname, String phone, String gender, String address, String city, String province, String pincode, Date dob, String imageURL) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.province = province;
        this.dob = dob;
        this.imageURL = imageURL;
    }
}

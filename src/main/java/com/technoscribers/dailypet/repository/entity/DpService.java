package com.technoscribers.dailypet.repository.entity;

import com.technoscribers.dailypet.model.enumeration.ServiceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
//Table - Person
public class DpService {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String phone;
    private String type;
    private String address;
    private String city;
    private String province;
    private String pinCode;
    @OneToOne
    @NotNull
    private User user;

    @OneToOne
    private Person person;

    public DpService(String name, String phone, String type, String address, String city, String province, String pinCode) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.address = address;
        this.city = city;
        this.province = province;
        this.pinCode = pinCode;
    }
}

package com.technoscribers.dailypet.repository.entity;

import com.technoscribers.dailypet.model.enumeration.ServiceType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PetService {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String phone;
    private ServiceType type;
    private String address;
    private String city;
    private String province;
    private String pinCode;
}

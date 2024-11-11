package com.technoscribers.dailypet.repository.entity;

import com.technoscribers.dailypet.model.enumeration.RoleName;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
//Table - roles
public class Roles {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private Boolean isActive;

    public Roles() {
    }
}

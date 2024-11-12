package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Date start;
    private Date end;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private PetDetails pet;
    private String dosageInstructions;

    public Medication(String name, Date start, Date end, Boolean isActive, PetDetails pet, String dosageInstructions) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.isActive = isActive;
        this.pet = pet;
        this.dosageInstructions = dosageInstructions;
    }
}

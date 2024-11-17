package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private Date scheduledDate;
    private Date previousDate;
    private String description;
    private Boolean isActive; //send notification if isActive true
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private PetDetails pet;

    public Vaccine(String name, Date scheduledDate, Date previousDate, String description, Boolean isActive, PetDetails pet) {
        this.name = name;
        this.scheduledDate = scheduledDate;
        this.previousDate = previousDate;
        this.description = description;
        this.isActive = isActive;
        this.pet = pet;
    }
}

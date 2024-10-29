package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String location;
    private Date date;
    private String description;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private PetDetails pet;

    public Appointment(String title, String location, Date date, String description, Boolean isActive, PetDetails pet) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.description = description;
        this.isActive = isActive;
        this.pet = pet;
    }
}

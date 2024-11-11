package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PetDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Date dob;
    private String gender;
    @Column(unique = true)
    private String idNo;
    private Float weight;
    private String weightUnit;
    @ManyToOne
    @NotNull
    private Breed breed;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private String imageURL;

   /* @OneToOne
    private Images image;*/

    public PetDetails(String name, Date dob, String gender, String idNo, Float weight,String weightUnit, Breed breed, User owner, String imageURL) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.idNo = idNo;
        this.weight = weight;
        this.breed = breed;
        this.owner = owner;
        this.weightUnit = weightUnit;
        this.imageURL = imageURL;
    }

}

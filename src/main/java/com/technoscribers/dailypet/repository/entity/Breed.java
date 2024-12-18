package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer lifeSpan;
    private String temperament;
    private String description;

    @ManyToOne
    @NotNull
    private PetType petType;
}

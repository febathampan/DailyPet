package com.technoscribers.dailypet.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BreedModel {
    private Long id;
    private Integer lifeSpan;
    private String temperament;
    private String name;
    private String typeOfPet;
    private String category;
    private String petTypeDescription;
}

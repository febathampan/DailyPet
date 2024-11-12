package com.technoscribers.dailypet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationModel {
    private Long id;
    private String name;
    private Date start;
    private Date end;
    private Boolean isActive;
    private Long petId;
    private String dosageInstructions;
}

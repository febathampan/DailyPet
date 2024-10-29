package com.technoscribers.dailypet.model;

import lombok.Data;

import java.util.Date;

@Data
public class MedicationModel {
    private Long id;
    private String name;
    private Date start;
    private Date end;
    private Boolean isActive;
    private Long petId;
}

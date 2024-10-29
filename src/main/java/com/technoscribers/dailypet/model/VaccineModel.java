package com.technoscribers.dailypet.model;

import lombok.Data;

import java.util.Date;

@Data
public class VaccineModel {
    private Long id;
    private String name;
    private Date scheduledDate;
    private Date previousDate;
    private String description;
    private Boolean isActive;
    private Long petId;
}

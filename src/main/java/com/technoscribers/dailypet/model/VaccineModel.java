package com.technoscribers.dailypet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineModel {
    private Long id;
    private String name;
    private Date scheduledDate;
    private Date previousDate;
    private String description;
    private Boolean isActive;
    private Long petId;
}

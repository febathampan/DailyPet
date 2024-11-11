package com.technoscribers.dailypet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentModel {
    private Long id;
    private String title;
    private String location;
    private Date date;
    private String description;
    private Boolean isActive;
    private Long petId;
}

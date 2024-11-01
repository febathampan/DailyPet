package com.technoscribers.dailypet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementModel {
    private Long id;
    private String post;
    private LocalDate posted;
    private LocalDate expire;
    private Boolean isActive;
    private Long userId;
}

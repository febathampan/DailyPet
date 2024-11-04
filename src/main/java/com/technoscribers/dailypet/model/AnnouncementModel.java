package com.technoscribers.dailypet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementModel {
    private Long id;
    private String title;
    private String post;
    private LocalDateTime publish;
    private LocalDateTime expire;
    private Boolean isActive;
    private Long userId;
}

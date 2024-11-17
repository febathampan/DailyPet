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
    private LocalDateTime createdDate;
    private Boolean isActive; // save as draft has isActive flag false
    private Long userId;
    private String imageURL;
}

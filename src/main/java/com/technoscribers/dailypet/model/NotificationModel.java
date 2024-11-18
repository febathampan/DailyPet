package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.model.enumeration.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationModel {
    private Long id;
    private NotificationType type;
    private String details;
    private LocalDateTime createdDate;
    private String imageURL;
    private String petName;
}

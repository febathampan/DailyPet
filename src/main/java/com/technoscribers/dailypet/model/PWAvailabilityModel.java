package com.technoscribers.dailypet.model;

import com.technoscribers.dailypet.repository.entity.DpService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PWAvailabilityModel {
    private Long id;
    private DayOfWeek weekDay;
    private String startTime;
    private String endTime;
    private Boolean publish;
    private Long serviceId;
}

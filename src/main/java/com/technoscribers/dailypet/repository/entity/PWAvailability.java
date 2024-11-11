package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Entity
@Data
@NoArgsConstructor
public class PWAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private DayOfWeek weekDay;
    private String startTime;
    private String endTime;
    private Boolean publish;
    @ManyToOne
    @NotNull
    private DpService service;

    public PWAvailability(DayOfWeek weekDay, String startTime, String endTime, Boolean publish, DpService service) {
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.publish = publish;
        this.service = service;
    }
}

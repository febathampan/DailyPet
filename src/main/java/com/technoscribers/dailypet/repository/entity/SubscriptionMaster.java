package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
//Table - subscription_master
public class SubscriptionMaster {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String teer;
    private BigDecimal charge;
    private Integer frequencyMonths;
    private Boolean isActive;

}

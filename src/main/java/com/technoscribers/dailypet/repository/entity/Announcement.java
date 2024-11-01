package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String post;
    private LocalDate posted;
    private LocalDate expire;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Announcement(String post, LocalDate posted, LocalDate expire, Boolean isActive, User owner) {
        this.post = post;
        this.posted = posted;
        this.expire = expire;
        this.isActive = isActive;
        this.owner = owner;
    }
}

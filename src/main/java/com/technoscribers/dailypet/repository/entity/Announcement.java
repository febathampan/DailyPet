package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String post;
    private String title;
    private LocalDateTime publish;
    private LocalDateTime expire;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Announcement(String title, String post, LocalDateTime publish, LocalDateTime expire, Boolean isActive, User owner) {
        this.title = title;
        this.post = post;
        this.publish = publish;
        this.expire = expire;
        this.isActive = isActive;
        this.owner = owner;
    }
}

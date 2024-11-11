package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor

//Table - User
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private Boolean isActive;
    private Instant createdOn;
    private Instant lastLogin;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;

    public User(String email, String password, Boolean isActive, Instant createdOn, Instant lastLogin, Roles role) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.createdOn = createdOn;
        this.lastLogin = lastLogin;
        this.roles = role;
    }

}

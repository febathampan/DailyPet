package com.technoscribers.dailypet.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Data
@NoArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Blob image;
    @OneToOne
    private Person person;
    @OneToOne
    private PetDetails petDetails;
    @OneToOne
    private DpService dpService;
}

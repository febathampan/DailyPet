package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByPetId(Long petId);
}

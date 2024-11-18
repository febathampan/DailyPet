package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByPetId(Long petId);

    List<Vaccine> findByPetIdAndIsActiveIsTrue(Long petId);
}

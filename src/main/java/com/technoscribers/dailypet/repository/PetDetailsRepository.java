package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.PetDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDetailsRepository extends JpaRepository<PetDetails, Long> {
}

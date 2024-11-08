package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.PWAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PWAvailabilityRepository extends JpaRepository<PWAvailability, Long> {
    List<PWAvailability> findByServiceId(Long serviceId);
}

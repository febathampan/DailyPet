package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPetId(Long petId);

    List<Appointment> findByPetIdAndIsActiveIsTrue(Long petId);
}

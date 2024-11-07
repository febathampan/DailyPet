package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.PetDetails;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetDetailsRepository extends JpaRepository<PetDetails, Long> {
    List<PetDetails> findByOwner(User user);
}

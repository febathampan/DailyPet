package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.model.AnnouncementModel;
import com.technoscribers.dailypet.repository.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByIsActive(Boolean flag);

    List<Announcement> findByIsActiveAndOwnerId(Boolean aTrue, Long ownerId);

    List<Announcement> findByIsActiveAndExpireLessThan(Boolean flag, LocalDate date);
}

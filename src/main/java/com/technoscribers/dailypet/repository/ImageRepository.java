package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {
}

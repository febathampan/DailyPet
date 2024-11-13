package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.DpService;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DpServiceRepository extends JpaRepository<DpService, Long> , JpaSpecificationExecutor<DpService> {
    DpService findByUser(User user);
}

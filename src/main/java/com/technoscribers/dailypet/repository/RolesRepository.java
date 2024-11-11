package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.model.enumeration.RoleName;
import com.technoscribers.dailypet.repository.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query("Select r from Roles  r where r.name = :role")
    Roles findByName(@Param("role")String role);
    Optional<Roles> findById(Long id);
}

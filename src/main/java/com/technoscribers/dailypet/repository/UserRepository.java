package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); //Email is unique
}

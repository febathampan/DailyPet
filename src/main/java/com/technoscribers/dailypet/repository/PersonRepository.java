package com.technoscribers.dailypet.repository;

import com.technoscribers.dailypet.repository.entity.Person;
import com.technoscribers.dailypet.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUser(User user);

    Optional<Person> findByUserId(Long userId);
}

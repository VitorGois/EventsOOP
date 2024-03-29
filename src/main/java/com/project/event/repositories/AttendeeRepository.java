package com.project.event.repositories;

import java.util.Optional;

import com.project.event.entities.Attendee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {

    @Query("SELECT a FROM Attendee a")
    Page<Attendee> find(Pageable pageRequest);

    @Query("SELECT a FROM Attendee a WHERE a.email = :email")
    public Optional<Attendee> findByEmail(String email);
}

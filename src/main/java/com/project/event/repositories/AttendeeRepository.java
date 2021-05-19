package com.project.event.repositories;

import com.project.event.entities.Attendee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    @Query(
        "SELECT at FROM Attendee at " +
                " WHERE " +
                " ( LOWER(at.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
                " ( LOWER(at.email) LIKE LOWER(CONCAT('%', :email, '%')))"
)
   public Page<Attendee> find(Pageable pageRequest, @Param("name") String name, @Param("email") String email);
}

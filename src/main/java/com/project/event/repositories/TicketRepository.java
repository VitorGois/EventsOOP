package com.project.event.repositories;

import java.time.Instant;

import com.project.event.entities.Ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(
            "SELECT t FROM Ticket t " +
                    "WHERE " +
                    "(LOWER(e.type) LIKE LOWER(CONCAT('%', :type, '%'))) AND " +
                    "(LOWER(e.date) LIKE LOWER(CONCAT('%', :date, '%')))"
    )
    Page<Ticket> find(Pageable pageRequest, @Param("type") String type, @Param("date") Instant date);
    Page<Ticket> find(Pageable pageRequest);
}
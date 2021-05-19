package com.project.event.repositories;

import com.project.event.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(
            "SELECT e FROM Event e " +
                    "WHERE " +
                    "(LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
                    "(LOWER(e.description) LIKE LOWER(CONCAT('%', :description, '%')))"
    )
    Page<Event> find(Pageable pageRequest, @Param("name") String name, @Param("description") String description);
}

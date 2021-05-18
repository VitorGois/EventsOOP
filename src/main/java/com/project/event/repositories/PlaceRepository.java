package com.project.event.repositories;

import com.project.event.entities.Place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query(
        "SELECT pl FROM Place pl " +
                " WHERE " +
                " ( LOWER(pl.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
                " ( LOWER(pl.address) LIKE LOWER(CONCAT('%', :address, '%')))"
)
   public Page<Place> find(Pageable pageRequest, @Param("name") String name, @Param("address") String address);
}

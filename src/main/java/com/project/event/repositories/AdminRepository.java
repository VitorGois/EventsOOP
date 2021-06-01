package com.project.event.repositories;

import java.util.Optional;

import com.project.event.entities.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("SELECT a FROM Admin a ")
    public Page<Admin> find(Pageable pageRequest);
    
    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    public Optional<Admin> findByEmail(String email);

}

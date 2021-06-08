package com.project.event.services;

import com.project.event.dtos.admin.AdminDto;
import com.project.event.dtos.admin.AdminInsertDto;
import com.project.event.dtos.admin.AdminUpdateDto;
import com.project.event.entities.Admin;
import com.project.event.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Page<AdminDto> readAdminList(PageRequest pageRequest) {
        try {
            Page<Admin> adminList = this.adminRepository.find(pageRequest);
            return adminList.map(admin -> new AdminDto(admin));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public AdminDto readAdminById(Long id) {
        Admin adminEntity = adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        try {
            return new AdminDto(adminEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public AdminDto createAdmin(AdminInsertDto adminInsertDto) {
        this.verifyEmailExistence(adminInsertDto.getEmail());

        try {
            Admin adminEntity = new Admin(adminInsertDto);
            adminEntity = this.adminRepository.save(adminEntity);
            return new AdminDto(adminEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the admin on the database");
        }
    }

    public AdminDto updateAdmin(Long id, AdminUpdateDto adminUpdateDto) {
        Admin adminEntity = adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        this.verifyEmailExistence(adminUpdateDto.getEmail());

        try {
            adminEntity.setEmail(adminUpdateDto.getEmail());
            adminEntity = this.adminRepository.save(adminEntity);

            return new AdminDto(adminEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating data");
        }
    }

    public void removeAdmin(Long id) {
        adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        try {
            this.adminRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This admin can't be deleted");
        }
    }

    private void verifyEmailExistence(String email) {
        Optional<Admin> opAdmin = this.adminRepository.findByEmail(email);
        
        if(opAdmin.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail already used");
        }
    }

}

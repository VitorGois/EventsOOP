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

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Page<AdminDto> readAdminList(PageRequest pageRequest) {
        Page<Admin> adminList = this.adminRepository.find(pageRequest);

        return adminList.map(admin -> new AdminDto(admin));
    }

    public AdminDto readAdminById(Long id) {
        Optional<Admin> opAdmin = adminRepository.findById(id);
        Admin admin = opAdmin.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
        return new AdminDto(admin);
    }

    public AdminDto createAdmin(AdminInsertDto adminInsertDto) {
        Admin adminEntity = new Admin(adminInsertDto);

        try {
            adminEntity = this.adminRepository.save(adminEntity);
            return new AdminDto(adminEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the admin on the database");
        }
    }

    public AdminDto updateAdmin(Long id, AdminUpdateDto adminUpdateDto) {
        try {
            Admin adminEntity = this.adminRepository.getOne(id);

            adminEntity.setEmail(adminUpdateDto.getEmail());
            adminEntity = this.adminRepository.save(adminEntity);

            return new AdminDto(adminEntity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    public void removeAdmin(Long id) {
        try {
            this.adminRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

}

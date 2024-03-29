package com.project.event.dtos.admin;

import com.project.event.entities.Admin;
import lombok.Data;

@Data
public class AdminDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    public AdminDto(Admin adminEntity) {
        this.id = adminEntity.getId();
        this.name = adminEntity.getName();
        this.email = adminEntity.getEmail();
        this.phoneNumber = adminEntity.getPhoneNumber();
    }

}

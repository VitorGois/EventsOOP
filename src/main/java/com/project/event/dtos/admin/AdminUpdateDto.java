package com.project.event.dtos.admin;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class AdminUpdateDto {

    @Email(message = "Must be an email : mail@mail.com")
    private String email;

}

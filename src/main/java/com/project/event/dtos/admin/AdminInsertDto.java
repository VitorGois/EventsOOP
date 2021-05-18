package com.project.event.dtos.admin;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdminInsertDto {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 5)
    private String name;

    @Email(message = "Must be an email : mail@mail.com")
    private String email;

    @NotBlank(message = "PhoneNumber is mandatory")
    @Size(min = 9)
    private String phoneNumber;

}

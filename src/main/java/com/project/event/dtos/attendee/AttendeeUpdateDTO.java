package com.project.event.dtos.attendee;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class AttendeeUpdateDTO {

    @Email(message = "Must be an email : mail@mail.com")
    private String email;

}

package com.project.event.dtos.attendee;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class AttendeeUpdateDTO {
    @Email(message = "Must be an email : mail@mail.com")
    private String email;

}

package com.project.event.dtos.attendee;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class AttendeeInsertDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 5)
    private String name;
    @Email(message = "Must be an email : mail@mail.com")
    private String email;
    @NotBlank(message = "Balance is mandatory")
    @Min(2)
    private Double balance;

}

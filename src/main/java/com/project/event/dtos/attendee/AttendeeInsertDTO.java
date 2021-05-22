package com.project.event.dtos.attendee;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AttendeeInsertDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 5)
    private String name;

    @Email(message = "Must be an email : mail@mail.com")
    private String email;

    @NotNull(message = "Balance is mandatory")
    @Min(0)
    private Double balance;

}

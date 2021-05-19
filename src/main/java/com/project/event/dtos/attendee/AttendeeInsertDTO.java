package com.project.event.dtos.attendee;

import lombok.Data;

@Data
public class AttendeeInsertDTO {
    private String name;
    private String email;
    private Double balance;

}

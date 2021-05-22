package com.project.event.dtos.attendee;

import com.project.event.entities.Attendee;
import lombok.Data;

@Data
public class AttendeeDTO {

    private Long id;
    private String name;
    private String email;
    private Double balance;

    public AttendeeDTO(Attendee atEntity) {
        this.id = atEntity.getId();
        this.name = atEntity.getName();
        this.email = atEntity.getEmail();
        this.balance = atEntity.getBalance();
    }

}   
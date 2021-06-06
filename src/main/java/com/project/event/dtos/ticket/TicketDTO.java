package com.project.event.dtos.ticket;

import com.project.event.entities.TicketType;

import lombok.Data;
@Data
public class TicketDTO {
    private TicketType type;
    private String attendeeName;

    public TicketDTO(TicketType type, String attendeeName){
        this.type= type;
        this.attendeeName= attendeeName;
    }
}

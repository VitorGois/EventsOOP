package com.project.event.dtos.ticket;

import com.project.event.entities.TicketType;

import lombok.Data;

@Data
public class TicketData {
    
    public TicketType type;
    public String attendeeName;

    public TicketData(TicketType type, String attendeeName) {
        this.type = type;
        this.attendeeName = attendeeName;
    }

}

package com.project.event.dtos.ticket;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.project.event.entities.TicketType;

import lombok.Data;

@Data
public class TicketInsertDto {
    
    @NotNull(message = "Attendee ID is mandatory")
    private Long attendeeId;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

}

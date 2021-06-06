package com.project.event.dtos.ticket;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;


public class TicketInsertDTO {
    @NotNull(message = "Attendee ID is mandatory")
    private Long attendeeId;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
}

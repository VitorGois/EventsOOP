package com.project.event.dtos.ticket;

import java.util.List;

import lombok.Data;

@Data
public class TicketDTO {

    List<TicketData> tickets;
    Long totalPaidTickets;
    Long totalFreeTickets;
    Long totalSoldPaidTickets;
    Long totalSoldFreeTickets;

    public TicketDTO(List<TicketData> tickets, Long totalPaidTickets, Long totalFreeTickets, Long totalSoldPaidTickets, Long totalSoldFreeTickets){
        this.tickets= tickets;
        this.totalPaidTickets= totalPaidTickets;
        this.totalFreeTickets = totalFreeTickets;
        this.totalSoldPaidTickets = totalSoldPaidTickets;
        this.totalSoldFreeTickets = totalSoldFreeTickets;
    }

}

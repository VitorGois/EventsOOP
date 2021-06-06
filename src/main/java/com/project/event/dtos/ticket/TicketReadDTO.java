package com.project.event.dtos.ticket;

import java.util.List;


import lombok.Data;
@Data
public class TicketReadDTO {
    List<TicketDTO> tiList;
    Long totalPaidTickets;
    Long totalFreeTickets;
    Long totalSelledTicketsP;
    Long totalSelledTicketsF;

public TicketReadDTO(List<TicketDTO> tiList, Long totalPaidTickets, Long totalFreeTickets, Long totalSelledTicketsP, Long totalSelledTicketsF){
    this.tiList= tiList;
    this.totalPaidTickets= totalPaidTickets;
    this.totalFreeTickets = totalFreeTickets;
    this.totalSelledTicketsP = totalSelledTicketsP;
    this.totalSelledTicketsF = totalSelledTicketsF;
}
    
}

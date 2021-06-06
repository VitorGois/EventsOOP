package com.project.event.services;

import java.util.ArrayList;
import java.util.List;

import com.project.event.dtos.ticket.TicketDTO;
import com.project.event.dtos.ticket.TicketReadDTO;
import com.project.event.entities.Event;
import com.project.event.entities.Ticket;
import com.project.event.entities.TicketType;
import com.project.event.repositories.EventRepository;
import com.project.event.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

    @Autowired
    private EventRepository eventRepository;

    public TicketReadDTO readTicketList(Long idEvent) {
        Event eventEntity = eventRepository.findById(idEvent)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        try {
            List<Ticket> tiList = eventEntity.getTickets();
            List<TicketDTO> tiDTOList = new ArrayList<>();
            Long paidTickets=0l;
            Long freeTickets=0l;
            for (Ticket ticket : tiList) {
                tiDTOList.add(new TicketDTO(ticket.getType(), ticket.getAttendee().getName()));
                if(ticket.getType()==TicketType.PAID){
                    paidTickets++;
            }
                 else{
                freeTickets++;
                    }
        }
        return new TicketReadDTO(tiDTOList, eventEntity.getAmountPayedTickets(), eventEntity.getAmountFreeTickets(), paidTickets, freeTickets);
    } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

}
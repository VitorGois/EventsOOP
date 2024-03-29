package com.project.event.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.event.dtos.event.EventDto;
import com.project.event.dtos.ticket.TicketDTO;
import com.project.event.dtos.ticket.TicketData;
import com.project.event.dtos.ticket.TicketInsertDto;
import com.project.event.entities.Attendee;
import com.project.event.entities.Event;
import com.project.event.entities.Ticket;
import com.project.event.entities.TicketType;
import com.project.event.repositories.AttendeeRepository;
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

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public TicketDTO readTicketList(Long idEvent) {
        Event eventEntity = eventRepository.findById(idEvent)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        List<Ticket> ticketList = eventEntity.getTickets();
        List<TicketData> ticketDataList = new ArrayList<>();

        try {
            Long paidSoldTickets = 0l;
            Long freeSoldTickets = 0l;

            for (Ticket ticket : ticketList) {
                TicketData ticketData = new TicketData(ticket.getType(), ticket.getAttendee().getName());

                ticketDataList.add(ticketData);

                if (ticket.getType() == TicketType.PAID) {
                    paidSoldTickets++;
                } else {
                    freeSoldTickets++;
                }
            }

            return new TicketDTO(ticketDataList, eventEntity.getAmountPayedTickets(), eventEntity.getAmountFreeTickets(), paidSoldTickets, freeSoldTickets);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public TicketDTO purchaseTicket(Long idEvent, TicketInsertDto ticketInsertDto) {
        Event eventEntity = this.eventRepository.findById(idEvent)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        Attendee attendeeEntity = this.attendeeRepository.findById(ticketInsertDto.getAttendeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found"));

        this.verifyTicketAvailability(eventEntity, ticketInsertDto);

        try {
            Double price = 0.0;
            Ticket ticket = new Ticket(ticketInsertDto.getTicketType(), Instant.now(), price, attendeeEntity,
                    eventEntity);

            eventEntity.addTicket(ticket);

            eventEntity = this.eventRepository.save(eventEntity);
            this.ticketRepository.save(ticket);

            return this.readTicketList(idEvent);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error confirming presence at event.");
        }
    }

    public TicketDTO refundTicket(Long idEvent, TicketInsertDto ticketInsertDto) {
        Event eventEntity = this.eventRepository.findById(idEvent)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        Attendee attendeeEntity = this.attendeeRepository.findById(ticketInsertDto.getAttendeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found"));

        if (LocalDateTime.of(eventEntity.getStartDate(), eventEntity.getStartTime()).isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The event has already taken place, it is not possible to make a refund.");
        }

        try {
            Ticket ticketEntity = new Ticket();

            for (Ticket ticket : eventEntity.getTickets()) {
                if (ticket.getAttendee().equals(attendeeEntity) && ticket.getEvent().equals(eventEntity)) {
                    ticketEntity = ticket;
                }
            }

            eventEntity.removeTicket(ticketEntity);

            eventEntity = this.eventRepository.save(eventEntity);
            this.ticketRepository.delete(ticketEntity);

            return this.readTicketList(idEvent);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error returning ticket.");
        }
    }

    private void verifyTicketAvailability(Event eventEntity, TicketInsertDto ticketInsertDto) {
        LocalDateTime eventStartDateTime = LocalDateTime.of(eventEntity.getStartDate(), eventEntity.getStartTime());
        if (eventStartDateTime.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The event has already taken place, it is not possible to purchase tickets for it.");
        }

        if (ticketInsertDto.getTicketType() == TicketType.PAID && eventEntity.getAmountPayedTickets() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Paid tickets for the event are already sold out.");
        } else if (ticketInsertDto.getTicketType() == TicketType.FREE && eventEntity.getAmountFreeTickets() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Free tickets for the event are already sold out.");
        }
    }

}

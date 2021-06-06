package com.project.event.services;

import java.time.Instant;
import java.time.LocalDateTime;

import com.project.event.dtos.event.EventDto;
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
import org.springframework.web.server.ResponseStatusException;

public class TicketService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public EventDto purchaseTicket(Long idEvent, TicketInsertDto ticketInsertDto) {
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

            return new EventDto(eventEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error confirming presence at event.");
        }
    }

    public EventDto refundTicket(Long idEvent, TicketInsertDto ticketInsertDto) {
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

            return new EventDto(eventEntity);
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

package com.project.event.services;

import javax.persistence.EntityNotFoundException;

import com.project.event.dtos.ticket.TicketDTO;
import com.project.event.dtos.ticket.TicketInsertDTO;
import com.project.event.dtos.ticket.TicketUpdateDTO;
import com.project.event.entities.Ticket;
import com.project.event.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Page<TicketDTO> readAttendeeList(PageRequest pageRequest) {
        try {
            Page<Ticket> tiList = this.ticketRepository.find(pageRequest);
            return tiList.map(ti -> new TicketDTO(ti));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public TicketDTO readTicketById(Long id) {
        try {
            Ticket ticketEntity = ticketRepository.getOne(id);
            return new TicketDTO(ticketEntity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public TicketDTO createTicket(TicketInsertDTO ticketInsertDTO) {
        try {
            Ticket ticketEntity = new Ticket(ticketInsertDTO);
            ticketEntity = this.ticketRepository.save(ticketEntity);
            return new TicketDTO(ticketEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the ticket on the database");
        }
    }

    public TicketDTO updateTicket(Long id, TicketUpdateDTO ticketUpdateDTO) {
        try {
            Ticket ticketEntity = ticketRepository.getOne(id);

            ticketEntity = this.ticketRepository.save(ticketEntity);

            return new TicketDTO(ticketEntity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating data");
        }
    }

    public void removeTicket(Long id) {
        try {
            this.ticketRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This tickt can't be deleted");
        }
    }


}
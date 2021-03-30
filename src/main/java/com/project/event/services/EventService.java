package com.project.event.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.project.event.dtos.EventCreateDto;
import com.project.event.dtos.EventDto;
import com.project.event.dtos.EventUpdateDto;
import com.project.event.entities.Event;
import com.project.event.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service()
public class EventService {

    @Autowired
    private EventRepository eventRepo;
    

    public EventDto createEvent(EventCreateDto newEvent) {
        Event eventEntity = new Event(newEvent); 
        if (verifyDateAndTime(newEvent.getStartDate(), newEvent.getEndDate(), newEvent.getStartTime(), newEvent.getEndTime())) {
            try {
                eventEntity = this.eventRepo.save(eventEntity);
                return new EventDto(eventEntity);           
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the event on the database");
            }          
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date or time before initial.");
        }
        
    }

    public Page<EventDto> readEvents(PageRequest pageRequest, String name, String description, String place, LocalDate startDate) {
        Page<Event> eventList = this.eventRepo.find(pageRequest, name, description, place, startDate);
        
        return eventList.map(event -> new EventDto(event));
    }

    public EventDto readEventById(Long id) {
        Optional<Event> opEvent = eventRepo.findById(id);
        
        Event event = opEvent.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        
        return new EventDto(event);
    }

    public void removeEvent(Long id) {
        try {
            this.eventRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    public EventDto updateEvent(Long id, EventUpdateDto updateEvent) {
        try {
            if (verifyDateAndTime(updateEvent.getStartDate(), updateEvent.getEndDate(), updateEvent.getStartTime(), updateEvent.getEndTime())) {
                Event event = this.eventRepo.getOne(id);

                event.setStartDate(updateEvent.getStartDate());
                event.setEndDate(updateEvent.getEndDate());
                event.setStartTime(updateEvent.getStartTime());
                event.setEndTime(updateEvent.getEndTime());
                event = this.eventRepo.save(event);
                
                return new EventDto(event);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date or time before initial.");
            }
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    private Boolean verifyDateAndTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if (endDate.equals(startDate) && endTime.isAfter(startTime)) {
            return true;
        }
        else if (endDate.isAfter(startDate)) {
            return true;
        } else {
            return false;
        }
    }

}

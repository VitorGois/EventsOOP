package com.project.event.services;

import com.project.event.dtos.CreateEventDto;
import com.project.event.dtos.ReadEventDto;
import com.project.event.entities.Event;
import com.project.event.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service()
public class EventService {

    @Autowired
    private EventRepository eventRepo;
    

    public ReadEventDto createEvent(CreateEventDto newEvent) {
        if (newEvent.getEndDate().isAfter(newEvent.getStartDate()) && newEvent.getEndTime().isAfter(newEvent.getStartTime())) {
            Event eventEntity = new Event(newEvent);

            try {
                eventEntity = this.eventRepo.save(eventEntity);
                return new ReadEventDto(eventEntity);           
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the event on the database");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date or time before initial.");
        }
        
    }

}

package com.project.event.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.project.event.dtos.EventCreateDto;
import com.project.event.dtos.EventDto;
import com.project.event.dtos.EventUpdateDto;
import com.project.event.entities.Event;
import com.project.event.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service()
public class EventService {

    @Autowired
    private EventRepository eventRepo;
    

    public EventDto createEvent(EventCreateDto newEvent) {
        if (newEvent.getEndDate().isAfter(newEvent.getStartDate()) && newEvent.getEndTime().isAfter(newEvent.getStartTime())) {
            Event eventEntity = new Event(newEvent);

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

    public List<EventDto> readClients() {
        List<Event> eventList = this.eventRepo.findAll();

        return this.toDTOList(eventList);
    }

    public EventDto getEventById(Long id) {
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
            if (updateEvent.getEndDate().isAfter(updateEvent.getStartDate()) && updateEvent.getEndTime().isAfter(updateEvent.getStartTime())) {
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

    private List<EventDto> toDTOList(List<Event> eventList) {
        List<EventDto> eventListDto = new ArrayList<>();

        for(Event e : eventList) {
            EventDto eventDto = new EventDto(e);
            eventListDto.add(eventDto);
        }

        return eventListDto;
    }

}

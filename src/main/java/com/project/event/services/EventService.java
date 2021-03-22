package com.project.event.services;

import java.util.ArrayList;
import java.util.List;

import com.project.event.dtos.EventCreateDto;
import com.project.event.dtos.EventDto;
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

    private List<EventDto> toDTOList(List<Event> eventList) {
        List<EventDto> eventListDto = new ArrayList<>();

        for(Event e : eventList) {
            EventDto eventDto = new EventDto(e);
            eventListDto.add(eventDto);
        }

        return eventListDto;
    }

}

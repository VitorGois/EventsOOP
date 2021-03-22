package com.project.event.controllers;

import java.net.URI;

import com.project.event.dtos.CreateEventDto;
import com.project.event.dtos.ReadEventDto;
import com.project.event.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController()
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService eventService;

    @PostMapping()
    public ResponseEntity<ReadEventDto> createEvent(@RequestBody CreateEventDto newEvent) {
        ReadEventDto eventDto = this.eventService.createEvent(newEvent);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eventDto.getId()).toUri();

        return ResponseEntity.created(uri).body(eventDto);
    }

}

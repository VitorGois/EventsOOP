package com.project.event.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.project.event.dtos.EventCreateDto;
import com.project.event.dtos.EventDto;
import com.project.event.dtos.EventUpdateDto;
import com.project.event.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController()
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService eventService;

    @GetMapping()
    public ResponseEntity<List<EventDto>> getEvents() {
        List<EventDto> eventList = this.eventService.readClients();
        return ResponseEntity.ok(eventList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable() Long id) {
        EventDto dto = this.eventService.getEventById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping()
    public ResponseEntity<EventDto> postEvent(@RequestBody @Valid EventCreateDto newEvent) {
        EventDto eventDto = this.eventService.createEvent(newEvent);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eventDto.getId()).toUri();

        return ResponseEntity.created(uri).body(eventDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventUpdateDto updateEvent, @PathVariable Long id) {
        EventDto eventDto = this.eventService.updateEvent(id, updateEvent);
        return ResponseEntity.ok().body(eventDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        this.eventService.removeEvent(id);
        return ResponseEntity.noContent().build();
    }

}

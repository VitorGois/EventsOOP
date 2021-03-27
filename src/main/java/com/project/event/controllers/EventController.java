package com.project.event.controllers;

import java.net.URI;
import java.time.LocalDate;

import javax.validation.Valid;

import com.project.event.dtos.EventCreateDto;
import com.project.event.dtos.EventDto;
import com.project.event.dtos.EventUpdateDto;
import com.project.event.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController()
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService eventService;

    @GetMapping()
    public ResponseEntity<Page<EventDto>> getEvents(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
        @RequestParam(value = "name", defaultValue = "") String name,
        @RequestParam(value = "decription", defaultValue = "") String description,
        @RequestParam(value = "place", defaultValue = "") String place,
        @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate
        ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<EventDto> eventList = this.eventService.readClients(pageRequest, name, description, place, startDate);
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
    public ResponseEntity<EventDto> updateEvent(@RequestBody @Valid EventUpdateDto updateEvent, @PathVariable Long id) {
        EventDto eventDto = this.eventService.updateEvent(id, updateEvent);
        return ResponseEntity.ok().body(eventDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        this.eventService.removeEvent(id);
        return ResponseEntity.noContent().build();
    }

}

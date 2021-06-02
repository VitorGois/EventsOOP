package com.project.event.controllers;

import com.project.event.dtos.event.EventDto;
import com.project.event.dtos.event.EventInsertDto;
import com.project.event.dtos.event.EventUpdateDto;
import com.project.event.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

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
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "startDate", defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<EventDto> eventList = this.eventService.readEventList(pageRequest, name, description);
        return ResponseEntity.ok(eventList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable() Long id) {
        EventDto eventDto = this.eventService.readEventById(id);
        return ResponseEntity.ok(eventDto);
    }

    @PostMapping()
    public ResponseEntity<EventDto> postEvent(@Valid() @RequestBody() EventInsertDto eventInsertDto) {
        EventDto eventDto = this.eventService.createEvent(eventInsertDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eventDto.getId()).toUri();
        return ResponseEntity.created(uri).body(eventDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> putEvent(@Valid() @RequestBody() EventUpdateDto eventUpdateDto, @PathVariable() Long id) {
        EventDto eventDto = this.eventService.updateEvent(id, eventUpdateDto);
        return ResponseEntity.ok().body(eventDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable() Long id) {
        this.eventService.removeEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idEvent}/places/{idPlace}")
    public ResponseEntity<EventDto> connectEventPlace(@PathVariable() Long idEvent, @PathVariable() Long idPlace) {
        EventDto eventDto = this.eventService.connectEventPlace(idEvent, idPlace);
        return ResponseEntity.ok(eventDto);
    }

}

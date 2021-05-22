package com.project.event.controllers;

import com.project.event.dtos.attendee.AttendeeDTO;
import com.project.event.dtos.attendee.AttendeeInsertDTO;
import com.project.event.dtos.attendee.AttendeeUpdateDTO;
import com.project.event.services.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping("/attendees")
public class AttendeeController {

    @Autowired
    public AttendeeService attendeeService;

    @GetMapping()
    public ResponseEntity<Page<AttendeeDTO>> getAttendees(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<AttendeeDTO> attendeeList = this.attendeeService.readAttendeeList(pageRequest);
        return ResponseEntity.ok(attendeeList);
    }

    @GetMapping("{id}")
    public ResponseEntity<AttendeeDTO> getAttendeeById(@PathVariable() Long id) {
        AttendeeDTO dto = this.attendeeService.readAttendeeById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AttendeeDTO> postAttendee(@RequestBody() AttendeeInsertDTO attendeeInsertDTO) {
        AttendeeDTO attendeeDto = this.attendeeService.createAttendee(attendeeInsertDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(attendeeDto.getId()).toUri();
        return ResponseEntity.created(uri).body(attendeeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendeeDTO> putAttendee(@RequestBody() AttendeeUpdateDTO attendeeUpdateDTO, @PathVariable() Long id) {
        AttendeeDTO attendeeDto = this.attendeeService.updateAttendee(id, attendeeUpdateDTO);
        return ResponseEntity.ok().body(attendeeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendee(@PathVariable() Long id) {
        this.attendeeService.removeAttendee(id);
        return ResponseEntity.noContent().build();
    }

}


    



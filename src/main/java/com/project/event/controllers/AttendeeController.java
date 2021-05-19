package com.project.event.controllers;

import java.net.URI;

import com.project.event.dtos.attendee.AttendeeDTO;
import com.project.event.dtos.attendee.AttendeeInsertDTO;
import com.project.event.dtos.attendee.AttendeeUpdateDTO;
import com.project.event.services.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/attendees")
public class AttendeeController {

    @Autowired
    public AttendeeService atService;

    @GetMapping()
    public ResponseEntity<Page<AttendeeDTO>> getAttendee(

                @RequestParam(value = "page", defaultValue = "0") Integer page,
                @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
                @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                @RequestParam(value = "name", defaultValue = "") String name,
                @RequestParam(value = "email", defaultValue = "") String email
        ) {
            PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy );
            Page<AttendeeDTO> attendeelist=  this.atService.getAttendees(pageRequest,name,email);
            return ResponseEntity.ok(attendeelist);
        }
        @GetMapping("{id}")
        public ResponseEntity<AttendeeDTO> getAttendeeById(@PathVariable Long id) {
            AttendeeDTO dto = atService.getAttendeeById(id);
            return ResponseEntity.ok(dto);
        }
    
        @PostMapping
        public ResponseEntity <AttendeeDTO> insert (@RequestBody AttendeeInsertDTO insertDto){
            AttendeeDTO dto = atService.insert(insertDto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
            return ResponseEntity.created(uri).body(dto);
        }
    
        @DeleteMapping("/{id}")
            public ResponseEntity<Void> delete (@PathVariable() Long id){
                atService.delete(id);
                return ResponseEntity.noContent().build();
            }
    
        @PutMapping("/{id}")
        public ResponseEntity<AttendeeDTO> update(@RequestBody AttendeeUpdateDTO updateDTO, @PathVariable Long id){
            AttendeeDTO dto = atService.update(id, updateDTO);
            return ResponseEntity.ok().body(dto);
        }
        }


    



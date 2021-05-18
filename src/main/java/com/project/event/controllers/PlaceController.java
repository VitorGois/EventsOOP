package com.project.event.controllers;

import java.net.URI;

import com.project.event.dtos.place.PlaceDTO;
import com.project.event.dtos.place.PlaceInsertDTO;
import com.project.event.dtos.place.PlaceUpdateDTO;
import com.project.event.services.PlaceService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/places")
public class PlaceController {
    @Autowired
    public PlaceService plService;

    @GetMapping()
    public ResponseEntity<Page<PlaceDTO>> getPlace(

                @RequestParam(value = "page", defaultValue = "0") Integer page,
                @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
                @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                @RequestParam(value = "name", defaultValue = "") String name,
                @RequestParam(value = "address", defaultValue = "") String address
        ) {
            PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy );
            Page<PlaceDTO> placelist=  this.plService.getPlaces(pageRequest,name,address);
            return ResponseEntity.ok(placelist);
        }
        @GetMapping("{id}")
        public ResponseEntity<PlaceDTO> getAttendeeById(@PathVariable Long id) {
            PlaceDTO dto = plService.getPlaceById(id);
            return ResponseEntity.ok(dto);
        }
    
        @PostMapping
        public ResponseEntity <PlaceDTO> insert (@RequestBody PlaceInsertDTO insertDto){
           PlaceDTO dto = plService.insert(insertDto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
            return ResponseEntity.created(uri).body(dto);
        }
    
        @DeleteMapping("/{id}")
            public ResponseEntity<Void> delete (@PathVariable() Long id){
                plService.delete(id);
                return ResponseEntity.noContent().build();
            }
    
        @PutMapping("/{id}")
        public ResponseEntity<PlaceDTO> update(@RequestBody PlaceUpdateDTO updateDTO, @PathVariable Long id){
            PlaceDTO dto = plService.update(id, updateDTO);
            return ResponseEntity.ok().body(dto);
        }
        }


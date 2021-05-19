package com.project.event.controllers;

import com.project.event.dtos.place.PlaceDTO;
import com.project.event.dtos.place.PlaceInsertDTO;
import com.project.event.dtos.place.PlaceUpdateDTO;
import com.project.event.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    public PlaceService placeService;

    @GetMapping()
    public ResponseEntity<Page<PlaceDTO>> getPlaces(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<PlaceDTO> placeList = this.placeService.readPlaceList(pageRequest);
        return ResponseEntity.ok(placeList);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable() Long id) {
        PlaceDTO dto = placeService.readPlaceById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping()
    public ResponseEntity<PlaceDTO> postPlace(@RequestBody() PlaceInsertDTO placeInsertDTO) {
        PlaceDTO placeDto = placeService.createPlace(placeInsertDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(placeDto.getId()).toUri();
        return ResponseEntity.created(uri).body(placeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDTO> putPlace(@RequestBody() PlaceUpdateDTO placeUpdateDto, @PathVariable() Long id) {
        PlaceDTO placeDto = placeService.updatePlace(id, placeUpdateDto);
        return ResponseEntity.ok().body(placeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable() Long id) {
        placeService.removePlace(id);
        return ResponseEntity.noContent().build();
    }

}


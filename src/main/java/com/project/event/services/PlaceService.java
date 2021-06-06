package com.project.event.services;

import com.project.event.dtos.place.PlaceDTO;
import com.project.event.dtos.place.PlaceInsertDTO;
import com.project.event.dtos.place.PlaceUpdateDTO;
import com.project.event.entities.Event;
import com.project.event.entities.Place;
import com.project.event.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public Page<PlaceDTO> readPlaceList(PageRequest pageRequest) {
        try {
            Page<Place> placeList = this.placeRepository.find(pageRequest);
            return placeList.map(place -> new PlaceDTO(place));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public PlaceDTO readPlaceById(Long id) {
        Place placeEntity = placeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        try {
            return new PlaceDTO(placeEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public PlaceDTO createPlace(PlaceInsertDTO placeInsertDto) {
        try {
            Place placeEntity = new Place(placeInsertDto);
            placeEntity = this.placeRepository.save(placeEntity);
            return new PlaceDTO(placeEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the admin on the database");
        }
    }

    public PlaceDTO updatePlace(Long id, PlaceUpdateDTO placeUpdateDTO) {
        Place placeEntity = placeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        try {
            placeEntity.setName(placeUpdateDTO.getName());
            placeEntity.setAddress(placeUpdateDTO.getAddress());
            placeEntity = this.placeRepository.save(placeEntity);

            return new PlaceDTO(placeEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating data");
        }
    }

    public void removePlace(Long id) {
        Place placeEntity = placeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        this.verifyEventsAssociation(placeEntity);

        try {
            this.placeRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This admin can't be deleted");
        }
    }

    public void verifyEventsAssociation(Place place) {
        Set<Event> events = place.getEvents();

        if(!events.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This place cannot be deleted as we already have events taking place in this place.");
        }
    }
}

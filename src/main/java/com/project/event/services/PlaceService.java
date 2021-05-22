package com.project.event.services;

import com.project.event.dtos.place.PlaceDTO;
import com.project.event.dtos.place.PlaceInsertDTO;
import com.project.event.dtos.place.PlaceUpdateDTO;
import com.project.event.entities.Place;
import com.project.event.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public Page<PlaceDTO> readPlaceList(PageRequest pageRequest) {
        Page<Place> placeList = this.placeRepository.find(pageRequest);
        return placeList.map(place -> new PlaceDTO(place));
    }

    public PlaceDTO readPlaceById(Long Id) {
        Optional<Place> opPlace = placeRepository.findById(Id);
        Place pl = opPlace.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        return new PlaceDTO(pl);
    }

    public PlaceDTO createPlace(PlaceInsertDTO placeInsertDto) {
        Place placeEntity = new Place(placeInsertDto);

        try {
            placeEntity = this.placeRepository.save(placeEntity);
            return new PlaceDTO(placeEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the place on the database");
        }
    }

    public PlaceDTO updatePlace(Long id, PlaceUpdateDTO placeUpdateDTO) {
        try {
            Place placeEntity = this.placeRepository.getOne(id);

            placeEntity.setName(placeUpdateDTO.getName());
            placeEntity.setAddress(placeUpdateDTO.getAddress());
            placeEntity = this.placeRepository.save(placeEntity);

            return new PlaceDTO(placeEntity);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

    public void removePlace(Long id) {
        try {
            this.placeRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
        catch(Exception e)
        {
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "You can't delete this place.");

        }
        }
    }


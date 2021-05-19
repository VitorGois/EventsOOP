package com.project.event.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.project.event.dtos.place.PlaceDTO;
import com.project.event.dtos.place.PlaceInsertDTO;
import com.project.event.dtos.place.PlaceUpdateDTO;
import com.project.event.entities.Place;
import com.project.event.repositories.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@Service
public class PlaceService {
    @Autowired
    private PlaceRepository plRepository;


    public Page<PlaceDTO> getPlaces(PageRequest pageRequest, String name, String address) {
       Page<Place> plList = this.plRepository.find(pageRequest, name, address);
       return plList.map(pl -> new PlaceDTO(pl));
    }
    public PlaceDTO getPlaceById(Long Id){
        Optional<Place>  op =  plRepository.findById(Id);
   
        Place pl = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Place not found"));
   
        return new PlaceDTO(pl);
       }
    public PlaceDTO update(Long id, PlaceUpdateDTO updateDTO) {
        try{
            Place entity = this.plRepository.getOne(id);
            entity.setName(updateDTO.getName());
            entity.setAddress(updateDTO.getAddress());
            entity = this.plRepository.save(entity);
            return new PlaceDTO(entity);

        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Place not found");
        }
    }
    public void delete(Long id) {
        try{
            plRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Place not found");

        }
    }
    public PlaceDTO insert(PlaceInsertDTO newPlace){
        Place plEntity = new Place(newPlace);
        try {
            plEntity = this.plRepository.save(plEntity);
            return new PlaceDTO(plEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the place on the database");
        }
    }
}

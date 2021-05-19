package com.project.event.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.project.event.dtos.attendee.AttendeeDTO;
import com.project.event.dtos.attendee.AttendeeInsertDTO;
import com.project.event.dtos.attendee.AttendeeUpdateDTO;
import com.project.event.entities.Attendee;
import com.project.event.repositories.AttendeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository atRepository;


    public Page<AttendeeDTO> getAttendees(PageRequest pageRequest, String name, String email) {
       Page<Attendee> atList = this.atRepository.find(pageRequest, name, email);
       return atList.map(at -> new AttendeeDTO(at));
    }
    public AttendeeDTO getAttendeeById(Long Id){
        Optional<Attendee>  op =  atRepository.findById(Id);
   
        Attendee at = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Attendee not found"));
   
        return new AttendeeDTO(at);
       }
    public AttendeeDTO update(Long id, AttendeeUpdateDTO updateDTO) {
        try{
            Attendee entity = atRepository.getOne(id);
            entity.setEmail(updateDTO.getEmail());
            entity = this.atRepository.save(entity);
            return new AttendeeDTO(entity);

        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Attendee not found");
        }
    }
    public void delete(Long id) {
        try{
            atRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Attendee not found");

        }
    }
    public AttendeeDTO insert(AttendeeInsertDTO newAttendee){
        Attendee atEntity = new Attendee(newAttendee);
        try {
            atEntity = this.atRepository.save(atEntity);
            return new AttendeeDTO(atEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the attendee on the database");
        }
    }
}


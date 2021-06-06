package com.project.event.services;

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

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    public Page<AttendeeDTO> readAttendeeList(PageRequest pageRequest) {
        try {
            Page<Attendee> atList = this.attendeeRepository.find(pageRequest);
            return atList.map(at -> new AttendeeDTO(at));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public AttendeeDTO readAttendeeById(Long id) {
        try {
            Attendee attendeeEntity = attendeeRepository.getOne(id);
            return new AttendeeDTO(attendeeEntity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public AttendeeDTO createAttendee(AttendeeInsertDTO attendeeInsertDTO) {
        try {
            this.verifyEmailExistence(attendeeInsertDTO.getEmail());
            Attendee attendeeEntity = new Attendee(attendeeInsertDTO);
            attendeeEntity = this.attendeeRepository.save(attendeeEntity);
            return new AttendeeDTO(attendeeEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the attendee on the database");
        }
    }

    public AttendeeDTO updateAttendee(Long id, AttendeeUpdateDTO attendeeUpdateDTO) {
        try {
            this.verifyEmailExistence(attendeeUpdateDTO.getEmail());
            Attendee attendeeEntity = attendeeRepository.getOne(id);

            attendeeEntity.setEmail(attendeeUpdateDTO.getEmail());
            attendeeEntity = this.attendeeRepository.save(attendeeEntity);

            return new AttendeeDTO(attendeeEntity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating data");
        }
    }

    public void removeAttendee(Long id) {
        try {
            this.attendeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This attendee can't be deleted");
        }
    }

    private void verifyEmailExistence(String email) {
        Optional<Attendee> opAttendee = this.attendeeRepository.findByEmail(email);
        opAttendee.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail already used"));
    }

}

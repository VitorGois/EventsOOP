package com.project.event.services;

import com.project.event.dtos.event.EventDto;
import com.project.event.dtos.event.EventInsertDto;
import com.project.event.dtos.event.EventUpdateDto;
import com.project.event.entities.Admin;
import com.project.event.entities.Event;
import com.project.event.entities.Place;
import com.project.event.repositories.AdminRepository;
import com.project.event.repositories.EventRepository;
import com.project.event.repositories.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PlaceRepository placeRepository;

    public Page<EventDto> readEventList(PageRequest pageRequest, String name, String description) {
        try {
            Page<Event> eventList = this.eventRepository.find(pageRequest, name, description);
            return eventList.map(event -> new EventDto(event));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public EventDto readEventById(Long id) {
        try {
            Event eventEntity = eventRepository.getOne(id);
            return new EventDto(eventEntity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error loading data from database");
        }
    }

    public EventDto createEvent(EventInsertDto eventInsertDto) {
        Optional<Admin> opAdmin = adminRepository.findById(eventInsertDto.getAdminId());
        Admin adminEntity = opAdmin
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        this.verifyDateAndTime(eventInsertDto.getStartDate(), eventInsertDto.getEndDate(),
                eventInsertDto.getStartTime(), eventInsertDto.getEndTime());

        try {
            Event eventEntity = new Event(eventInsertDto);
            eventEntity.setAdmin(adminEntity);
            eventEntity = this.eventRepository.save(eventEntity);

            return new EventDto(eventEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when saving the admin on the database");
        }
    }

    public EventDto updateEvent(Long id, EventUpdateDto eventUpdateDto) {
        this.verifyDateAndTime(eventUpdateDto.getStartDate(), eventUpdateDto.getEndDate(),
                eventUpdateDto.getStartTime(), eventUpdateDto.getEndTime());

        try {
            Event eventEntity = eventRepository.getOne(id);

            eventEntity.setStartDate(eventUpdateDto.getStartDate());
            eventEntity.setEndDate(eventUpdateDto.getEndDate());
            eventEntity.setStartTime(eventUpdateDto.getStartTime());
            eventEntity.setEndTime(eventUpdateDto.getEndTime());
            eventEntity.setAmountFreeTickets(eventUpdateDto.getAmountFreeTickets());
            eventEntity.setAmountPayedTickets(eventUpdateDto.getAmountPayedTickets());
            eventEntity.setPriceTicket(eventUpdateDto.getPriceTicket());
            eventEntity = this.eventRepository.save(eventEntity);

            return new EventDto(eventEntity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating data");
        }
    }

    public void removeEvent(Long id) {
        try {
            this.eventRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This event can't be deleted");
        }
    }

    public EventDto connectEventPlace(Long idEvent, Long idPlace) {
        Place placeEntity = this.placeRepository.findById(idPlace)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        Event eventEntity = this.eventRepository.findById(idEvent)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        this.verifyPlaceAvailability(placeEntity, eventEntity);

        try {
            eventEntity.addPlace(placeEntity);

            eventEntity = this.eventRepository.save(eventEntity);
            this.placeRepository.save(placeEntity);

            return new EventDto(eventEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when connect the event with the place.");
        }
    }

    public EventDto disconnectEventPlace(Long idEvent, Long idPlace) {
        Place placeEntity = this.placeRepository.findById(idPlace)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        Event eventEntity = this.eventRepository.findById(idEvent)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        try {
            eventEntity.removePlace(placeEntity);

            eventEntity = this.eventRepository.save(eventEntity);
            this.placeRepository.save(placeEntity);

            return new EventDto(eventEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Error when disconnect the event from the place.");
        }
    }

    private Boolean verifyDateAndTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if ((endDate.equals(startDate) && endTime.isAfter(startTime)) || endDate.isAfter(startDate)) {
            return true;
        } else if (endDate.isBefore(startDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The end date must be later than the initial date.");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The end time must be later than the start time.");
        }
    }

    private void verifyPlaceAvailability(Place place, Event event) {
        Set<Event> eventsOfPlaces = place.getEvents();

        for (Event e : eventsOfPlaces) {
            if (e.getEndDate().isAfter(event.getStartDate()) || e.getStartDate().isBefore(event.getEndDate())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "There is already an event that will be held on this date.");
            }
        }
    }

}

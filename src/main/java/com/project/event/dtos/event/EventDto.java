package com.project.event.dtos.event;

import com.project.event.entities.Event;
import com.project.event.entities.Place;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class EventDto {

    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String emailContact;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Double priceTicket;
    private Long adminId;
    private Set<Place> places;

    public EventDto(Event eventEntity) {
        this.id = eventEntity.getId();
        this.name = eventEntity.getName();
        this.description = eventEntity.getDescription();
        this.startDate = eventEntity.getStartDate();
        this.endDate = eventEntity.getEndDate();
        this.startTime = eventEntity.getStartTime();
        this.endTime = eventEntity.getEndTime();
        this.emailContact = eventEntity.getEmailContact();
        this.amountFreeTickets = eventEntity.getAmountFreeTickets();
        this.amountPayedTickets = eventEntity.getAmountPayedTickets();
        this.priceTicket = eventEntity.getPriceTicket();
        this.adminId = eventEntity.getAdmin().getId();
        this.places = eventEntity.getPlaces();
    }

}

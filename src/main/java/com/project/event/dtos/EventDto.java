package com.project.event.dtos;

import java.time.LocalDate;

import com.project.event.entities.Event;

public class EventDto {
    
    private Long id;
    private String name;
    private String description;
    private String place;
    private String emailContact;
    private LocalDate startDate;
    private LocalDate endDate;
    
    public EventDto(Event eventEntity) {
        this.id = eventEntity.getId();
        this.name = eventEntity.getName();
        this.description = eventEntity.getDescription();
        this.place = eventEntity.getPlace();
        this.emailContact = eventEntity.getEmailContact();
        this.startDate= eventEntity.getStartDate();
        this.endDate = eventEntity.getEndDate();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public String getEmailContact() {
        return emailContact;
    }
    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}

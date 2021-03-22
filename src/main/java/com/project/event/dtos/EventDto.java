package com.project.event.dtos;

import com.project.event.entities.Event;

public class EventDto {
    
    private Long id;
    private String name;
    private String description;
    private String place;
    
    public EventDto(Event eventEntity) {
        this.id = eventEntity.getId();
        this.name = eventEntity.getName();
        this.description = eventEntity.getDescription();
        this.place = eventEntity.getPlace();
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

}

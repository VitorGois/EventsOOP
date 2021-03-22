package com.project.event.dtos;

import com.project.event.entities.Event;

public class ReadEventDto {
    
    private String id;
    private String name;
    private String description;
    private String place;
    
    public ReadEventDto(Event eventEntity) {
        this.name = eventEntity.getName();
        this.description = eventEntity.getDescription();
        this.place = eventEntity.getPlace();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
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

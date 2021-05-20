package com.project.event.dtos.place;

import com.project.event.entities.Place;
import lombok.Data;

@Data
public class PlaceDTO {

    private Long id;
    private String name;
    private String address;

    public PlaceDTO(Place placeEntity) {
        this.id = placeEntity.getId();
        this.name = placeEntity.getName();
        this.address = placeEntity.getAddress();
    }

}

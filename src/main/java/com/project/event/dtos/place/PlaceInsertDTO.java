package com.project.event.dtos.place;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PlaceInsertDTO {
    @NotBlank(message = "Name of the place is mandatory")
    @Size(min = 5)   
    private String name;
    @NotBlank(message = "The address place is mandatory")
    @Size(min = 10) 
    private String address;

}

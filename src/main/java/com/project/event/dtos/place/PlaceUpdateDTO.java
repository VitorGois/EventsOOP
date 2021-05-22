package com.project.event.dtos.place;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PlaceUpdateDTO {

    @NotBlank(message = "Name of the place is mandatory")
    @Size(min = 5)
    private String name;

    @NotBlank(message = "The address place is mandatory")
    @Size(min = 10)
    private String address;
}

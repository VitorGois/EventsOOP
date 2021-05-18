package com.project.event.dtos.event;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventUpdateDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Double priceTicket;

}

package com.project.event.dtos.event;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventInsertDto {

    private Long adminId;
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

}

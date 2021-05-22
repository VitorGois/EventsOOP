package com.project.event.dtos.event;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventUpdateDto {

    @FutureOrPresent(message = "StartDate must be in the present or future")
    @NotNull(message = "StartDate is mandatory")
    private LocalDate startDate;

    @FutureOrPresent(message = "EndDate must be in the present or future")
    @NotNull(message = "EndDate is mandatory")
    private LocalDate endDate;

    @NotNull(message = "StartTime is mandatory")
    private LocalTime startTime;

    @NotNull(message = "EndTime is mandatory")
    private LocalTime endTime;

    @NotNull(message = "AmountFreeTickets is mandatory")
    @Min(0)
    private Long amountFreeTickets;

    @NotNull(message = "AmountPayedTickets is mandatory")
    @Min(0)
    private Long amountPayedTickets;

    @NotNull(message = "PriceTicket is mandatory")
    @Min(0)
    private Double priceTicket;

}

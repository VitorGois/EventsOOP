package com.project.event.dtos.event;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventInsertDto {

    @NotNull(message = "Admin ID is mandatory")
    private Long adminId;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 5)
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 20, max = 300)
    private String description;

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

    @Email(message = "Must be an email : mail@mail.com")
    private String emailContact;

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

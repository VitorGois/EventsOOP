package com.project.event.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.event.dtos.event.EventInsertDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "event")
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;

    @NonNull
    private LocalTime startTime;

    @NonNull
    private LocalTime endTime;

    @NonNull
    private String emailContact;

    @NonNull
    private Long amountFreeTickets;

    @NonNull
    private Long amountPayedTickets;

    @NonNull
    private Double priceTicket;

    @ManyToOne()
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "event")
    @Setter(AccessLevel.NONE)
    private List<Ticket> tickets = new ArrayList<>();

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "event_place", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "place_id"))
    @Setter(AccessLevel.NONE)
    private Set<Place> places;

    public Event(EventInsertDto eventInsertDto) {
        this.name = eventInsertDto.getName();
        this.description = eventInsertDto.getDescription();
        this.startDate = eventInsertDto.getStartDate();
        this.endDate = eventInsertDto.getEndDate();
        this.startTime = eventInsertDto.getStartTime();
        this.endTime = eventInsertDto.getEndTime();
        this.emailContact = eventInsertDto.getEmailContact();
        this.amountFreeTickets = eventInsertDto.getAmountFreeTickets();
        this.amountPayedTickets = eventInsertDto.getAmountPayedTickets();
        this.priceTicket = eventInsertDto.getPriceTicket();
    }

    public void addPlace(Place place) {
        this.places.add(place);
    }

    public void removePlace(Place place) {
        this.getPlaces().remove(place);
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

}

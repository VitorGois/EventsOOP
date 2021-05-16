package com.project.event.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    @NonNull private String name;
    @NonNull private String description;
    @NonNull private LocalDate startDate;
    @NonNull private LocalDate endDate;
    @NonNull private LocalTime startTime;
    @NonNull private LocalTime endTime;
    @NonNull private String emailContact;
    @NonNull private Long amountFreeTickets;
    @NonNull private Long amountPayedTickets;
    @NonNull private Double priceTicket;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "event_place",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id")
    )
    @Setter(AccessLevel.NONE) private List<Place> places = new ArrayList<>();

    public void addPlace(Place place) {
        this.places.add(place);
    }

}

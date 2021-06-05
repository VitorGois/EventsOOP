package com.project.event.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.event.dtos.place.PlaceInsertDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "place")
@Entity
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String address;

    @JsonBackReference
    @ManyToMany(mappedBy = "places")
    @Setter(AccessLevel.NONE)
    private Set<Event> events;

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public Place(PlaceInsertDTO newPlace) {
        this.name = newPlace.getName();
        this.address = newPlace.getAddress();
    }

}
